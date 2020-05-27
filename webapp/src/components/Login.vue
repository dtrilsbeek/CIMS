<template>
    <div>
        <!-- <form @submit.prevent="login"> -->
        <ul>
            <li>
                <h1>
                login
                </h1>
            </li>
            <li>
                <input type="text" name="username" placeholder="username" />
            </li> 
            <li>
                <input type="password" name="password" placeholder="password" />
            </li>
            <li>
                <button>login</button>
            </li>
        </ul>
    </div>
</template>

<script>
export default {
  name: 'Login',
  props: {
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

ul, li {
    margin: 0;
    padding: 0;
    border: 0;
    font-size: 100%;
    font: inherit;
    vertical-align: baseline;
}



h1 {
    font-size: 25px;
    margin: 2px 2px 2px 2px;
}

div {
    display: flex;
    justify-content: center;
}

ul {
    list-style-type: none;
}

input {
    box-shadow: .2em .2em 0 0 rgba(0, 0, 0, 0.25);
    margin: 0 0 8px 0;
    box-sizing: border-box;
    height: 32px;
}

ul, ul * {
    width: 320px;
}

button {
   
    background-color: #666666;
    color: white;
    border: none;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 14px 2px;
    cursor: pointer;
}

button:hover {
    transition-duration: .5s;
    background-color: #444444;
}
</style>