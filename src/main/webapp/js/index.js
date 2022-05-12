var app = new Vue({
    el: '#app',
    data() {
        return {
            pizzas : [],
            cart : {}
        }
    },
    mounted() {
        axios.get('/public/pizzas')
            .then(response => {
                this.pizzas = response.data.data;
            });
    },
    methods: {
        addPizza(pizza) {
            let cartId = localStorage.getItem('cart.id');
            if (!cartId) {
                cartId = -1;
            }
            axios.post('/public/cart/pizza?cartId=' + cartId +'&pizzaId=' + pizza.id)
                .then(response => {
                    if (response.data.success) {
                        localStorage.setItem('cart.id', response.data.data);
                        axios.get('/public/cart?cartId=' + response.data.data)
                            .then(response => {
                                this.cart = response.data.data;
                            })
                    }
                })
        }
    }
});