<script lang="ts" setup>
  import { computed, watch, ref } from 'vue';
  import ApexCharts from 'vue3-apexcharts';

  interface Point {
    x: number;
    y: number;
  }
  
  const props = defineProps<{
    points: Point[];
  }>();
  
  const series = ref([
    {
      name: 'f(x)',
      data: props.points.map(p => [p.x, p.y]),
    },
  ]);
  
  watch(
    () => props.points,
    (newPoints) => {
      series.value = [
        {
          name: 'f(x)',
          data: newPoints.map(p => [p.x, p.y]),
        },
      ];
    },
    { immediate: true, deep: true }
  );
  
  const chartOptions = computed(() => ({
    chart: {
      animations: {
        enabled: true,
        easing: 'easeinout',
        speed: 500,
      },
      zoom: { enabled: false },
    },
    xaxis: {
      type: 'numeric',
      title: { text: 'x' },
    },
    yaxis: {
      title: { text: 'y' },
      decimalsInFloat: 2,
    },
    stroke: {
      curve: 'smooth',
      width: 5,
    },
  }));
</script>

<template>
    <ApexCharts
      type="line"
      :options="chartOptions"
      :series="series"
      width="100%"
    />
</template>