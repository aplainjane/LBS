<script setup>
import Aside from "@/components/Aside.vue";
import Header from "@/components/header.vue";
</script>

<template>
  <!--    头部-->
  <Header/>
  <!--主体-->
  <div style="display: flex">
    <!--      侧边栏-->
    <Aside/>
    <!--      内容区域-->
    <RouterView style="flex: 1"/>
  </div>
</template>

<style scoped>

</style>