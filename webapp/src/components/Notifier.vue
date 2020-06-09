<template>
  <section class="alert-holder">
      <div class="alert" v-for="alert in alerts" :key="alert.queue">{{alert.msg}}
          <span class="timer"></span>
      </div>
  </section>
</template>

<script>
export default {
    data(){
        return {
            alerts: [],
            queueSize: 0,
        }
    },

    methods: {
        addAlert(message){
            this.queueSize ++;
            this.alerts.push({
                msg: message,
                queue: this.queueSize
            });

            setTimeout(() => {
                this.alerts.shift();
                if(this.alerts.length == 0){
                    this.queueSize = 0;
                }
            }, 3300);
        }

    }
}
</script>

<style src="@/assets/css/notifier.css">
</style>