<script setup>
 import { defineProps, defineEmits} from 'vue';  

 import axios from 'axios';

    
    //EMIT
    const emits = defineEmits(["backToIndex", "edit", "deletePizza"])
  


    // PROPS
    const props = defineProps({
        selectedPizza: {
            type: Object,
            required: false
        }
    });

    const deletePizza = async () => {
        const data = await axios.delete(
            `http://localhost:8080/api/pizza/delete/${props.selectedPizza.id}`
        );

        console.log("data", data);

        emits("deletePizza");
    }


</script>

<template>
    
    <section id="show" class="row">
        
        <div class="col">

            <div class="row justify-content-center ">
    
                <div class="col-6 g-4" >
    
                    <div class="card"  >
    
                        <img class="img-fluid" :src="props.selectedPizza.fotoUrl" alt="">
    
                        <div class="col">
                            {{ props.selectedPizza.nome }}
                        </div>

                        <div class="col">
                            {{ props.selectedPizza.descrizione }}
                        </div>

                        <div class="col">
                            {{ props.selectedPizza.prezzo }}
                        </div>
                        
                    </div>
    
                </div>
                
            </div>

            <div class="row justify-content-center ">

                <div class="col-auto g-3 text-center">
                  
                    <button class="btn btn-primary" @click="$emit('backToIndex')" type="button"> back </button>
                    
                </div>
              
                <div class="col-auto g-3 text-center">
                    
                    <button class="btn btn-warning" @click="$emit('edit', props.selectedPizza)" type="button"> edit </button>

                </div>

                <div class="col-auto g-3 text-center">
                    
                    <button class="btn btn-danger" @click="deletePizza" type="button"> delete </button>

                </div>




            </div>

        </div>

    </section>
</template>

<style lang="scss" scoped>
</style>