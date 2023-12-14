<script setup>
    // IMPORTS

    import { onMounted, ref } from 'vue';
    import axios from 'axios';
    import IndexComponent from './IndexComponent.vue';
    import ShowComponent from './ShowComponent.vue';
    import FormComponent from './FormComponent.vue';
   
    // DATA
    const pizzas= ref(null)

    const creating = ref(false)

    const selectedPizza=ref(null)

    const editing = ref(false)

    const oldPizza=ref(null)


    // Functions
    
    const getPizzas = async () => {
        const data = await axios.get("http://localhost:8080/api/pizza");
        pizzas.value = data.data;
    }
 
    const getSelectedPizza = (pizza)=>{
        console.log('pizza', pizza)
        selectedPizza.value=pizza
    }
    
    const resetSelectedPizza=()=>{

        selectedPizza.value=null;
        
    }

    const resetForm=()=>{
        creating.value=false;
        editing.value=false;
        oldPizza.value=null;
    }   

    const getOldPizza=(pizza)=>{
        resetSelectedPizza();
        editing.value=true;
        oldPizza.value=pizza;
    }

    const afterCreate=()=>{

        creating.value=false;

        getPizzas();
    }

    const afterEdit=()=>{

        editing.value=false;

        getPizzas();
    }

    const afterDelete=()=>{

        resetSelectedPizza();

        getPizzas();
    }

    // Hooks
    onMounted(getPizzas)
</script>

<template>
    <main>

        <div class="container">

            <IndexComponent v-if="selectedPizza==null && (creating==false && editing==false)" :pizzas="pizzas" @that-pizza="getSelectedPizza" @create="creating=true"/>
    
            <ShowComponent v-if="selectedPizza!=null && (creating==false && editing==false)" :selectedPizza="selectedPizza" @back-to-index="resetSelectedPizza" @edit="getOldPizza" @delete-pizza="afterDelete"/>
    
            <FormComponent v-if="creating==true || editing==true" :oldPizza="oldPizza" @created="afterCreate" @edited="afterEdit" @back="resetForm" />

        </div>

    </main>
</template>

<style lang="scss" scoped>

</style>