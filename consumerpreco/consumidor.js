import amqp from 'amqplib';

const fila = 'PRECO';

amqp
  .connect({
    hostname: 'localhost',
    port: 5672,
    username: 'admin',
    password: 123456,
  })
  .then((conexao) => {
    conexao.createChannel().then((channel) => {
      channel.consume(fila, (message) => {
        console.log(message.content.toString());
      }, {noAck: true});
    }).catch(error => console.error(error));
  })
  .catch((error) => console.error(error));
