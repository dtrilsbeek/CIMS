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
            lastAlertTimestamp: 0
        }
    },

    methods: {
        addAlert(message){
            if(this.isTimedOut()){
                return;
            }

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
        },

        isTimedOut(){
            let isTimedOut = false;
            const timestamp = new Date();
            if(timestamp - this.lastAlertTimestamp > 500){
                this.lastAlertTimestamp = timestamp;
            }
            else{
                isTimedOut = true;
            }
            return isTimedOut;
        }

    }
}
</script>

<style src="@/assets/css/notifier.css">
</style>