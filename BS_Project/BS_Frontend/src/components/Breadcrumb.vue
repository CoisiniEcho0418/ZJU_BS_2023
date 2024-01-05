<template>
  <div>
    <!-- 面包屑 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item v-for="item in breadcrumbList"
                          :key="'breadcrumb_' + item.path">
        {{ item.meta.title }}
      </el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
export default {
  components: {},
  props: {},
  data () {
    return {
    };
  },
  watch: {},
  computed: {
    breadcrumbList () {
      return this.$route.matched;
    },
  },
  methods: {},
  created () { },
  mounted () { }
};
</script>
<style scoped>
</style>