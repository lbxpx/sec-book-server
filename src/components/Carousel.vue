<template>
  <div style="display: grid; place-items: center;">
    <el-carousel :interval="5000" arrow="always">
      <el-carousel-item v-for="item in imageList" >
        <img :src="item.url" no-repeat style="object-fit: fill;height:100%; width:100%;" />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>



<script setup lang="ts">
import utils from '~/utils/utils';

//定义跑马灯的图片路径
const imageList = [
  { url: utils.getAssetsFile('1.JPG') },
  { url: utils.getAssetsFile('2.JPG') },
  { url: utils.getAssetsFile('3.JPG') },
];

</script>
<style scoped>
.el-carousel {
  width: 1200px;
}

.el-carousel__item img {
  width: 100%;
  height: 100%;
  color: #475669;
  opacity: 0.75;
  margin: 0;
  text-align: center;
}
</style>
