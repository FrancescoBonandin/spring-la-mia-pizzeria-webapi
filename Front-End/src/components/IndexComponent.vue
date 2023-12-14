<script setup>
  import { defineProps, defineEmits} from 'vue';  

    //EMIT
    const emit = defineEmits(["thatPizza", "create"])



    // PROPS
    const props = defineProps({
        pizzas: {
            type: Array,
            required: true,
            default:null
        }
    });

</script>

<template>


     <section id="index" class="row">
    
        <h2 class="text-center my-4">
            Index
        </h2>


        <div class="text-center">

            <button class="  btn btn-primary" @click="$emit('create')" type="button"> create </button>

        </div>

        <div class="col" >

            <div class="row justify-content-start ">

                <div class="col-4 g-4" v-for="pizza in pizzas" :key="pizza.id">

                    <div class="card" @click="$emit('thatPizza', pizza)" >

                        <img class="img-fluid" :src="pizza.fotoUrl" alt="">

                        <div class="col">
                            {{ pizza.nome }}
                        </div>
                        
                    </div>

                </div>
                
            </div>
        </div>

    </section>
</template>

<style lang="scss" scoped>
.card{
    cursor: pointer;
}
</style>