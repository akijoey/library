<template>
  <svg class="icon-font" aria-hidden="true">
    <use :xlink:href="iconName"></use>
  </svg>
</template>

<script>
  export default {
    name: 'IconFont',
    props: {
      iconClass: {
        type: String,
        required: true
      }
    },
    computed: {
      iconName() {
        return `#icon-${this.iconClass}`
      }
    }
  }
</script>

<style>
  .icon-font {
    width: 24px;
    height: 1em;
    vertical-align: -0.15em;
    fill: currentColor;
    overflow: hidden;
    margin-right: 5px;
    font-size: 18px;
  }
</style>