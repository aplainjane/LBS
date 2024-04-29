<template>
  <el-row class="tac">
    <el-col :span="12">
      <el-menu
          style="width: 350px; min-height: calc(100vh - 50px);"
          class="el-menu-vertical-demo"
      >
        <el-menu-item index="1">
          <el-button type="info" plain :icon="Location" @click="getLoc">自身位置</el-button>
        </el-menu-item>

        <el-menu-item index="2">
          <el-button type="info" plain :icon="Notification" @click="add">添加贴图</el-button>
        </el-menu-item>

        <el-menu-item index="3">
          <el-button type="info" plain :icon="ChatDotSquare" @click="cmtShow">查看贴图</el-button>
        </el-menu-item>
        <el-menu-item>
          <el-checkbox v-model="mapSetting.enableScrollWheelZoom" label="鼠标缩放"/>

        </el-menu-item>
        <el-menu-item>
          <el-checkbox v-model="mapSetting.enableDragging" label="拖拽"/>

        </el-menu-item>
        <el-menu-item>
          <el-checkbox v-model="mapSetting.enableInertialDragging" label="惯性拖拽"/>

        </el-menu-item>

        <el-menu-item>
          <el-checkbox v-model="mapSetting.enablePinchToZoom" label="双指缩放地图"/>

        </el-menu-item>

        <el-menu-item>
          <el-checkbox v-model="mapSetting.enableKeyboard" label="键盘操作"/>

        </el-menu-item>

        <el-menu-item>
          <el-checkbox v-model="mapSetting.enableDoubleClickZoom" label="双击缩放，左键双击放大、右键双击缩小"/>

        </el-menu-item>

        <el-menu-item>
          <el-checkbox v-model="mapSetting.enableContinuousZoom" label="双击平滑缩放效果"/>

        </el-menu-item>

        <el-menu-item>
          <el-checkbox v-model="mapSetting.enableTraffic" label="显示交通路况"/>

        </el-menu-item>
      </el-menu>
    </el-col>
  </el-row>

  <div style="flex: 1">
    <div class="state" v-if="!isLoadingLoc && !isError">
      <el-text size="large" type="primary">定位:</el-text>
      <br />
      <el-text>
        城市 - {{ location.address?.province }}-{{ location.address?.city }}-{{ location.address?.district }}-{{
          location.address?.street
        }}
      </el-text>
      <div style="margin-top: 20px">
        <span>纬度 - {{ location.point?.lat }}</span>
        <br />
        <span>经度 - {{ location.point?.lng }}</span>
      </div>

      <br />
      <span>定位精度 - {{ location.accuracy }}m</span>
    </div>
    <div class="state" v-else-if="isError">
      <el-text size="large" type="danger">
        出错了，{{ status }}
      </el-text>
    </div>
    <div class="state" v-else>
      <el-text type="info">
        定位中...
      </el-text>
    </div>
    <div>
      <el-select
          v-model="type"
          placeholder="Select"
          size="large"
          style="width: 240px"
      >
        <el-option
            v-for="item in typeData"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>
    </div>

    <br />
    <br />
    <BMap
        v-bind="$attrs"
        height="700px"
        :heading="0"
        :tilt="45"
        :center="location.point || undefined"
        ref="map"
        :zoom="19"
        :minZoom="3"
        :mapType="type"
        :enableDragging="mapSetting.enableDragging"
        :enableInertialDragging="mapSetting.enableInertialDragging"
        :enableScrollWheelZoom="mapSetting.enableScrollWheelZoom"
        :enableContinuousZoom="mapSetting.enableContinuousZoom"
        :enableDoubleClickZoom="mapSetting.enableDoubleClickZoom"
        :enableKeyboard="mapSetting.enableKeyboard"
        :enablePinchToZoom="mapSetting.enablePinchToZoom"
        :enableTraffic="mapSetting.enableTraffic"

        @initd="handleInitd"
        @click="handleClick"
    >
      <template v-if="!isLoadingLoc">
        <BMarker :position="location.point"></BMarker>
        <BCircle
            strokeStyle="solid"
            strokeColor="#0099ff"
            :strokeOpacity="0.8"
            fillColor="#0099ff"
            :fillOpacity="0.5"
            :center="location.point"
            :radius="location.accuracy"
        />
      </template>
      <template v-if="!isLoadingGeo && !isEmpty">
        <BMarker :position="point"></BMarker>
        <BLabel
            style="color: #333; font-size: 9px"
            :position="result.point"
            :content="`地址: ${result?.address} 所属商圈:${result?.business} 最匹配地点: ${
            result?.surroundingPois[0]?.title || '无'
          }`"
        />
      </template>
      <BLocation />
    </BMap>
    <el-dialog
      v-model="dialogVisible"
      title="上传贴图"
      width="50%"
      >
      <el-upload
          v-model:file-list="fileList"
          action="http://192.168.137.1:9091/file/upload"
          list-type="picture-card"
          ref="uploadRef"
          :on-preview="handlePictureCardPreview"
          :on-remove="handleRemove"
          :before-upload="beforeUpload"
          :data="dataForm"
          :auto-upload="false"
          multiple
      >
        <el-icon><Plus /></el-icon>
      </el-upload>

      <el-dialog v-model="previewVisible">
        <img w-full :src="dialogImageUrl" alt="Preview Image" />
      </el-dialog>
      <el-button style="width: 150px; height: 50px" type="primary" @click="submitUpload">上传</el-button>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>

import {ChatDotSquare, Notification, Location, Plus} from "@element-plus/icons-vue";
import {ref} from "vue";
import {
  BMap,
  useBrowserLocation,
  type MapProps,
  type MapType,
  usePointGeocoder,
  type PointGeocoderResult
} from "vue3-baidu-map-gl";
import request from "@/utils/request";
import {
  ElMessage,
  ElMessageBox,
  type UploadInstance,
  type UploadProps,
  type UploadRequestOptions,
  type UploadUserFile
} from "element-plus";

const type = ref<MapType>('BMAP_NORMAL_MAP')

const map = ref()

const typeData = [
  {
    value: 'BMAP_NORMAL_MAP',
    label: 'BMAP_NORMAL_MAP',
  },
  {
    value: 'BMAP_EARTH_MAP',
    label: 'BMAP_EARTH_MAP',
  },
  {
    value: 'BMAP_SATELLITE_MAP',
    label: 'BMAP_SATELLITE_MAP',
  }
]
const { get: getLoc, location, isLoading: isLoadingLoc, isError, status } = useBrowserLocation(null, () => {
  map.value.resetCenter()
})
const { get: getGeo, result, isLoading: isLoadingGeo, isEmpty } = usePointGeocoder<PointGeocoderResult>()
const point = ref({ lng: 116.30793520652882, lat: 40.05861561613348 })
const markerPoint = point

let dialogVisible=ref(false)
let mapSetting = ref<MapProps>({
  enableDragging: false,
  enableInertialDragging: true,
  enableScrollWheelZoom: false,
  enableContinuousZoom: true,
  enableResizeOnCenter: true,
  enableDoubleClickZoom: false,
  enableKeyboard: true,
  enablePinchToZoom: true,
  enableAutoResize: true,
  enableTraffic: false
})

function handleInitd() {
  getLoc()
  point.value=location.value.point
  getGeo(point.value)
}
function handleClick(e) {
  markerPoint.value = e.latlng
  getGeo(e.latlng)
}

const form = ref({
  point:point.value,
  id: -1
})
const add=()=>{
  dialogVisible.value=true;
  form.value={
    point:point.value,
    id: 1
  }
}

//上传相关
const fileList = ref<UploadUserFile[]>([])
const dataForm = ref({
  id: 1,
  latitude: point.value.lat,
  longitude: point.value.lng

});
const dialogImageUrl = ref('')
const previewVisible = ref(false)

const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles)
}

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
  dialogImageUrl.value = uploadFile.url!
  previewVisible.value = true
}
const beforeUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png' && rawFile.type !== 'image/bmp' && rawFile.type !== 'image/gif') {
    ElMessage.error('上传的图片必须要是 JPG/PNG/BMP/GIF 格式!')
    return false
  }
  return true
}

const uploadRef = ref<UploadInstance>()

const submitUpload = () => {
  console.log(fileList.value)
  uploadRef.value!.submit()
}

//一个范围内的贴图查询
let distance = ref(1)
const url = ref('')
const cmtVisible = ref(false)

const cmtShow = () => {
  cmtVisible.value = true
  getImgSec()
  form.value
}
const getImgSec = () => {
  request.get('/file/image', {  // 确保URL与后端设置的路由一致
    params: {
      longtitude: point.value.lng,
      latitude: point.value.lat,
      distance: distance.value
    },
    responseType: 'blob'  // 设置响应类型为Blob来处理二进制数据
  }).then(res => {
    const urlCreator = window.URL || window.webkitURL;
    url.value = urlCreator.createObjectURL(res.data);  // 创建一个临时URL用于图片显示
    console.log(url.value);
  })
};


</script>
