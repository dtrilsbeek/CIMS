<template>
        <ul>
            <li><h1>Add <span>Situation</span></h1></li>
            <li><input type="number"  placeholder="Input latitude" ></li>
            <li><input type="number"  placeholder="Input longitude" ></li>

            <li>
                <select  class="dropdown">
                    <option v-for="number in 5" :key="number.number">{{number}}</option>
                </select>
            </li>

            <li><textarea  placeholder="Input information"/></li>

            <li><button type="button" >Send</button></li>
            <li><button type="button" >Move selected marker</button></li>
      </ul>
</template>

<script>
export default {

}
</script>

<style src="@/assets/css/form.css" scoped></style>
<style src="@/assets/css/create-situation.css" scoped></style>