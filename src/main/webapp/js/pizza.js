var app = new Vue({
    el: '#app',
    data() {
        return {
            pizzas : [],
            ingredients : [],
            create : false,
            newPizza : {},
            newIngredient : {}
        }
    },
    mounted() {
        axios.get('/admin/pizzas')
            .then(response => {
                this.pizzas = response.data.data;
            });
        axios.get('/admin/ingredients')
            .then(response => {
                this.ingredients = response.data.data;
            })
    },
    methods: {
        addIngredient() {
            if (!this.newPizza.ingredients) {
                this.newPizza.ingredients = [];
            }
            this.newPizza.ingredients.push(this.newIngredient)
            this.newIngredient = {};
        },
        createPizza() {
            axios.post('/admin/pizza/create', this.newPizza)
                .then(response => {
                    if (response.data.success) {
                        this.newPizza = {};
                        this.newIngredient = {};
                        this.create = false;

                        axios.get('/admin/pizzas')
                            .then(response => {
                                this.pizzas = response.data.data;
                            })
                    }
                })
        }
    }
});