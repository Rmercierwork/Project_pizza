var app = new Vue({
    el: '#app',
    data() {
        return {
            cart: {}
        }
    },
    mounted() {
        this.loadPanier();
    },
    methods: {
        loadPanier() {
            let cartId = localStorage.getItem('cart.id');
            axios.get('/public/cart?cartId=' + cartId)
                .then(response => {
                    this.cart = response.data.data;
                })
        },
        removePizza(pizza) {
            axios.post('/public/pizza/remove', pizza.id)
                .then(response => {
                    if (response.data.success) {
                        this.loadPanier();
                    }
                })
        }
    }
});