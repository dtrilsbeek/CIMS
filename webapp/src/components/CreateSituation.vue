<template>
        <ul>
            <li><h1>Add <span>Situation</span></h1></li>
            <li><input type="number" class="as-text" v-model="event.lat" placeholder="Input latitude" ></li>
            <li><input type="number" class="as-text" v-model="event.lon" placeholder="Input longitude" ></li>

            <li>
                <select  class="dropdown">
                    <option v-for="number in 5" :key="number.number">{{number}}</option>
                </select>
            </li>

            <li><textarea  placeholder="Input information"/></li>

            <li><button type="button" >Send</button></li>
            <li><button type="button" :class="{disabled: !selectedMarker}" >
                <div class="disabled-tooltip">Geen marker geselecteerd</div>
                Move selected marker
                </button></li>
      </ul>
</template>

<script>
export default {
    props:{
        selectedMarker: {
            type: [Object,Boolean],
            required: true
        },

        event: {
            type: Object,
            required: true
        }
    }
}
</script>

<style src="@/assets/css/form.css" scoped></style>
<style src="@/assets/css/create-situation.css" scoped></style>