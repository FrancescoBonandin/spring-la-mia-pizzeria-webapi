<script setup>

import {defineProps, defineEmits, ref } from 'vue';
import axios from 'axios';

// PROPS
const props = defineProps({
    oldPizza: {
        type: Object,
        required: false,
        default: null
    }
});

// DATA

const pizza = ref({
    nome: "",
    descrizione: "",
    fotoUrl: "",
    prezzo: ""
});

if (props.oldPizza != null) {

    pizza.value = {
        nome: props.oldPizza.nome,
        descrizione: props.oldPizza.descrizione,
        fotoUrl: props.oldPizza.fotoUrl,
        prezzo: props.oldPizza.prezzo,

    };

}

// EMITS
const emits = defineEmits(["back", "created", "edited"]);


// FUNCTIONS
const submitWAxios = async () => {
    if (props.oldPizza == null) {
     
        const data = await axios.post(
            "http://localhost:8080/api/pizza/create", 
            pizza.value
        );

        console.log("data", data);

        emits("created");

        return;
    } 

    const data = await axios.put(
        `http://localhost:8080/api/pizza/edit/${props.oldPizza.id}`, 
        pizza.value
    );

    console.log("data", data);

    emits("edited", pizza.value);
}

</script>

<template>
    <section id="form" class="row justify-content-center">
    
        <form @submit.prevent="submitWAxios" class="col-6" method="POST" >
            <h2 class="text-center my-5">

                <span>
                    {{ props.oldPizza == null ? "Create new " : "Update" }}
                </span>

                <span>
                    Pizza
                </span>

            </h2>

            <div>
        
                <label class="form-label" for="nome">
                    Nome
                </label>
                    
                <input class="mb-2 form-control" type="text" id="nome" name="nome" placeholder="nome" v-model="pizza.nome" >
                
            </div>
            
            <div>

                <label class="form-label" for="descrizione">
                    Descrizione
                </label>
                
                <textarea class="mb-2 form-control" id="descrizione" name="descrizione" placeholder="descrizione" v-model="pizza.descrizione"></textarea>
                
            </div>
            
            <div>

                <label class="form-label" for="fotoUrl">
                    Foto Url
                </label>
                
                <input class="mb-2 form-control" type="text" id="fotoUrl" name="fotoUrl" placeholder="fotoUrl" v-model="pizza.fotoUrl">
            </div>
            
            <div>

                <label class="form-label" for="prezzo">
                    Prezzo
                </label>
                
                <input class="mb-2 form-control" type="number" id="prezzo" name="prezzo" placeholder="prezzo" step="0.01" v-model="pizza.prezzo" >
                
            </div>
            
            <div class=" mb-4 row justify-content-evenly">

                <div class="col-auto">

                    <button class="btn btn-primary" type="submit">
                        {{ props.oldPizza == null ? "Create" : "Update" }}
                    </button>

                </div>

                <div class="col-auto">

                    <button class="btn btn-primary" type="button" @click="$emit('back')">
                        Back
                    </button>

                </div>
                
            </div>
        
        </form>

    </section>
</template>

<style lang="scss" scoped>
</style>