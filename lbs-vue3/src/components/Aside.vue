<template>
  <el-row class="tac">
    <el-col :span="12">
      <el-menu
          style="width: 200px; min-height: calc(100vh - 50px);"
          default-active="1"
          class="el-menu-vertical-demo"
      >

        <router-link to="Personnel">
          <el-menu-item index="1">
            <el-icon><Avatar /></el-icon>
            <span>人员信息</span>
          </el-menu-item>
        </router-link>

        <router-link to="event">
          <el-menu-item index="2">
            <el-icon><Notification /></el-icon>
            <span>待办事项</span>
          </el-menu-item>
        </router-link>
        <router-link to="PsnToEvent">
          <el-menu-item index="3">
            <el-icon><Notification /></el-icon>
            <span>个人事项管理</span>
          </el-menu-item>
        </router-link>

      </el-menu>
    </el-col>
  </el-row>
</template>

<script setup>

import {Avatar, Notification} from "@element-plus/icons-vue";


</script>
