<template>
  <div>

  <!-- <img alt="Vue logo" src="../assets/logo.png"> -->

    <ul>
      <li>
        <h1>
          Send
        </h1>
      </li>
      <li>
         <input type="number" min="0" placeholder="Input latitude"/>
      </li> 
      <li>
       <input type="number" min="0" placeholder="Input longitude"/>
      </li>
      <li>
        <input type="number" min="0" placeholder="Input type"/>
      </li>
      <li>
        <textarea placeholder="Input information"/>
      </li>
      <li>
        <button>Send</button>
      </li>
    </ul>

  </div>
</template>

<script>
export default {
  name: 'Home',
  props: {
    // msg: String
  }
}
</script>

<style src="@/assets/css/home.css" scoped></style>
