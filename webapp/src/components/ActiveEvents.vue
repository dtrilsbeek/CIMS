<template>
  <div>
      <ul ref="eventScrollElement" class="activeEvents">
        <li v-for="event in events" :key="event.id">
            Type: {{event.type}}
        </li>
      </ul>
        Total: {{events.length}}
   </div>
</template>

<script>
export default {

    props: {
        bus: Object
    },
    data() {
        return {
            events: [],
            eventSource: null,
        }
    },
    methods: {
        retrieveEventsByRegionBounds(bounds) { 
            
            this.events = [];

            if(this.eventSource != null && this.eventSource.readyState !== EventSource.CLOSED) {
                this.eventSource.close();
            }

            const params = {
                sx: bounds[0][0],
                sy: bounds[0][1],
                ex: bounds[1][0],
                ey: bounds[1][1]
            };

            let urlParams = new URLSearchParams(params).toString();

            this.eventSource = new EventSource(`http://localhost:8083/events/stream?${urlParams}`);
            this.eventSource.onmessage = (event) => {
                
                //events retrieved by region bounds
                const data = JSON.parse(event.data);
                console.log(data.id);
                
                // let existing = this.events.filter(x => x.id == data.id).length;
                
                // if(existing == 0) {
                //     this.events.push(data);
                // }
                // console.log(data);
                
            };
        },
    },

    created() {
        this.bus.$on('retrieve-by-bounds', bounds => {
            // console.log('efjio');
            this.retrieveEventsByRegionBounds(bounds);
        })
        //  this.bus.$on('add-event-to-stream', data => {
               
        //       //incoming event
        //     //   console.log(data);
              
        //       this.events.push(data);    
        //       console.log(this.events.length);           
        //  })
    },
}
</script>

<style src="@/assets/css/active-events.css" scoped>
</style>
