<template>
  <el-row class="tac">
    <el-col :span="12">
      <el-menu
          style="width: 350px; min-height: calc(100vh - 50px);"
          class="el-menu-vertical-demo"
      >
        <el-menu-item index="1">
          <el-button type="info" plain :icon="Location" @click="get">自身位置</el-button>
        </el-menu-item>

        <el-menu-item index="2">
          <el-button type="info" plain :icon="Notification">添加贴图</el-button>
        </el-menu-item>

        <el-menu-item index="3">
          <el-button type="info" plain :icon="ChatDotSquare">查看评价</el-button>
        </el-menu-item>
        <el-menu-item>
          <input type="checkbox" v-model="mapSetting.enableScrollWheelZoom" />
          鼠标缩放
        </el-menu-item>
        <el-menu-item>
          <input type="checkbox" v-model="mapSetting.enableDragging" />
          拖拽
        </el-menu-item>
        <el-menu-item>
          <input type="checkbox" v-model="mapSetting.enableInertialDragging" />
          惯性拖拽
        </el-menu-item>

        <el-menu-item>
          <input type="checkbox" v-model="mapSetting.enablePinchToZoom" />
          双指缩放地图
        </el-menu-item>

        <el-menu-item>
          <input type="checkbox" v-model="mapSetting.enableKeyboard" />
          键盘操作
        </el-menu-item>

        <el-menu-item>
          <input type="checkbox" v-model="mapSetting.enableDoubleClickZoom" />
          双击缩放，左键双击放大、右键双击缩小
        </el-menu-item>

        <el-menu-item>
          <input type="checkbox" v-model="mapSetting.enableContinuousZoom" />
          双击平滑缩放效果
        </el-menu-item>

        <el-menu-item>
          <input type="checkbox" v-model="mapSetting.enableTraffic" />
          显示交通路况
        </el-menu-item>
      </el-menu>
    </el-col>
  </el-row>

  <div style="flex: 1">
    <div class="state" v-if="!isLoading && !isError">
      <h5>定位:</h5>
      <span>
        城市 - {{ location.address?.province }}-{{ location.address?.city }}-{{ location.address?.district }}-{{
          location.address?.street
        }}
      </span>
      <span>纬度 - {{ location.point?.lat }}</span>
      <span>经度 - {{ location.point?.lng }}</span>
      <br />
      <span>定位精度 - {{ location.accuracy }}m</span>
    </div>
    <div class="state" v-else-if="isError">出错了，{{ status }}</div>
    <div class="state" v-else>定位中...</div>
    <div>
      地图类型：
      <select class="mySelect" name="" id="" v-model="type">
        <option value="BMAP_NORMAL_MAP">常规地图 BMAP_NORMAL_MAP</option>
        <option value="BMAP_EARTH_MAP">地球模式 BMAP_EARTH_MAP</option>
        <option value="BMAP_SATELLITE_MAP">卫星图 BMAP_EARTH_MAP</option>
      </select>
    </div>

    <br />
    <br />
    <BMap
        v-bind="$attrs"
        height="700px"
        :heading="64.5"
        :tilt="73"
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

        @initd="get"
    >
      <template v-if="!isLoading">
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
    </BMap>
  </div>
</template>

<script lang="ts" setup>

import {ChatDotSquare, Notification, Location} from "@element-plus/icons-vue";
import {ref} from "vue";
import {BMap, useBrowserLocation, type MapProps, type MapType} from "vue3-baidu-map-gl";

const type = ref<MapType>('BMAP_NORMAL_MAP')

const map = ref()
const { get, location, isLoading, isError, status } = useBrowserLocation(null, () => {
  map.value.resetCenter()
})

let mapSetting = ref<MapProps>({
  enableDragging: true,
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



</script>
