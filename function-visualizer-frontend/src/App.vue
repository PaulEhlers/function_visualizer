<script setup lang="ts">
import FunctionChart from './components/FunctionChart.vue';
import { ref } from 'vue';
import axios from 'axios';

interface Point {
  x: number;
  y: number;
}

const expression = ref('')
const points = ref<Point[]>([
  { x: -2, y: 4 },
  { x: -1, y: 1 },
  { x: 0, y: 0 },
  { x: 1, y: 1 },
  { x: 2, y: 4 },
])

async function submitExpression() {
  try {
    const response = await axios.post('http://localhost:8080/evaluate', {
      expression: expression.value,
      range: {
        from: 0.1,
        to: 3,
      },
      steps: 0.1,
    })
    points.value = response.data
  } catch (error) {
    console.error(error)
  }
}

</script>

<style scoped>
.main-container {
  padding: 2rem;
  font-family: sans-serif;
  max-width: 800px;
  margin: 0 auto;
}

.form {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
}

input {
  flex: 1;
  padding: 0.5rem;
  font-size: 1rem;
}

button {
  padding: 0.5rem 1rem;
  font-size: 1rem;
  cursor: pointer;
}

h1 {
  text-align: center;
}

h4 {
  text-align: center;
  color: grey;
}

</style>


<template>
  <main class="main-container">
    <h1>Function Visualizer</h1>
    <h4>Made by Paul Ehlers</h4>
    <FunctionChart :points="points" />
    <form @submit.prevent="submitExpression" class="form">
      <input
        v-model="expression"
        type="text"
        placeholder="Enter a math expression (e.g. sin(x))"
      />
      <button type="submit">Plot</button>
    </form>
  </main>
</template>