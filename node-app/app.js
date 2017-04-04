var express = require('express')
var request = require('request')

var app = express()

app.get('/hello', function (req, res) {
  res.send('Hello World!')
})

app.get('/message', function (req, res, next) {
  request('http://localhost:8080/generate-chat-message?receiver=Hello',
    function (error, response, body) {
      if (error) {
        console.log('ERROR with user request!')
        return res.sendStatus(response.statusCode)
      }
      body = JSON.parse(body)

      // console.log(body)
      // var text
      // for (var key in body) {
      //   text += 'Index is: ' + key + '\n Description is:  ' + body[key]
      // }
      // console.log("Got a response: ", text)

      res.send(body.msg)
  })
})

app.listen(3000, function () {
  console.log('Express app listening port 3000')
})
