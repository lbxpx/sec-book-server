<template>
  <div class="bigbox">
    销量信息页面
  </div>
  <el-table :data="tableData" border:true style="width: 100%">
    <el-table-column prop="date" label="商品编号" width="180" />
    <el-table-column prop="name" label="商品介绍" width="300" />
    <el-table-column prop="price" label="商品价格" width="180" />
    <el-table-column prop="type" label="商品类型" width="180" />
 
 
 
    <el-table-column prop="inventory" label="商品库存" width="180" />
    <el-table-column prop="sold" label="商品销量" width="180" style="color: red !important"></el-table-column>
  </el-table>
</template>

<script setup lang="ts">
import { ref } from 'vue';

let tableData = ref([]); 
</script>

<style scoped lang="scss">
.bigbox {
  width: 100%;
  height: 6%;
  font-size: 25px;
  padding-left: 10px;
  padding-top: 10px;
  /* background-color: bisque; */
}
 
.button1 {
  margin-left: 900px;
}
</style>