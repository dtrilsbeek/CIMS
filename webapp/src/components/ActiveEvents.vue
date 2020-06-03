<template>
  <div class="wrapper">
      Actieve events ({{events.length}})
      <ul class="activeEvents">
        <li v-for="event in events" :key="event.id" v-on:click="navigateToEvent(event)">
            {{event.type}}
        </li>
      </ul>
   </div>
</template>

<script>
import config from '@/components/rest/RestConfig'

export default {

    props: {
        bus: Object
    },
    data() {
        return {
            streamUrl: config.getUrl('events', 'stream'),
            events: [],
            eventSource: null,
        }
    },
    methods: {
        retrieveEventsByRegionBounds(bounds) { 
            // this.events.push(
            //     {lat: 44.45,lon: 44.44,type: 'ambulance',description: 'description'
            // })
            //  this.events.push(
            //     {lat: 44.45,lon: 44.44,type: 'ambulance',description: 'description'
            // })
            //  this.events.push(
            //     {lat: 44.45,lon: 44.44,type: 'ambulance',description: 'description'
            // })
            //  this.events.push(
            //     {lat: 44.45,lon: 44.44,type: 'ambulance',description: 'description'
            // })
            //  this.events.push(
            //     {lat: 44.45,lon: 44.44,type: 'ambulance',description: 'description'
            // })

            this.events = [];

            if(this.eventSource != null && this.eventSource.readyState !== EventSource.CLOSED) {
                this.eventSource.close();
            }

            const params = {
                sx: bounds[0][1],
                sy: bounds[0][0],
                ex: bounds[1][1],
                ey: bounds[1][0]
            };

            let urlParams = new URLSearchParams(params).toString();

            this.eventSource = new EventSource(`${this.streamUrl}?${urlParams}`);
            this.eventSource.onmessage = (event) => {
                
                //events retrieved by region bounds
                const data = JSON.parse(event.data);

                data.type = this.getIconTypeString(data.type);

                this.events.push(data);          
            };
        },
        navigateToEvent(event) {
            this.$emit("move-to-event", event);            
        },
        getIconTypeString(type) {
            switch (type) {
                case 1:
                    return "fireTruck"
                case 2:
                    return "ambulance"
                case 3:
                    return "fire"
                case 4:
                    return "police"
                default:
                    return "ambulance"
            }
        },
    },

    created() {
        this.bus.$on('retrieve-by-bounds', bounds => {

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
