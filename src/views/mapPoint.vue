<template>
  <div class="demo-image">
    <div v-for="fit in fits" :key="fit" class="block">
      <span class="demonstration">{{ fit }}</span>
      <el-image style="width: 100px; height: 100px" :src="url" :fit="fit" />
    </div>
  </div>
  <el-button @click="getImg">获取图片</el-button>
</template>

<script lang="ts" setup>
import type { ImageProps } from 'element-plus'
import request from "@/utils/request";
import {ref} from "vue";

const fits = [
  'fill',
  'contain',
  'cover',
  'none',
  'scale-down',
] as ImageProps['fit'][]

const getImg = () => {
  request.get('/file/image', {  // 确保URL与后端设置的路由一致
    params: {
      imageId: 2
    },
    responseType: 'blob'  // 设置响应类型为Blob来处理二进制数据
  }).then(res => {
    const urlCreator = window.URL || window.webkitURL;
    url.value = urlCreator.createObjectURL(res.data);  // 创建一个临时URL用于图片显示
    console.log(url.value);
  })
};
const url = ref('https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg')

</script>

<style scoped>
.demo-image .block {
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  display: inline-block;
  width: 20%;
  box-sizing: border-box;
  vertical-align: top;
}
.demo-image .block:last-child {
  border-right: none;
}
.demo-image .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}
</style>
