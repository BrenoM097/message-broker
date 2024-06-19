const amqp = require('amqplib')

const queue = 'PRECO'

amqp.connect({
    host:'localhost',
    port: 5672,
    username: 'admin',
    password: '12345'
})
    .then((connection) => {
        connection.createChannel()
        .then((canal) => {
            canal.consume(queue, (message) => {
                console.log(message.content.toString())
            })
        })
        .catch((error) => console.log(error))
    })
    .catch((error) => console.log(error))