var express = require('express')
var request = require('request')

var BASE_URL = 'http://localhost:8080'

var app = express()

app.get('/hello', function (req, res) {
  res.send('Hello World!')
})

app.get('/message/:receiver', function (req, res, next) {
  var receiver = req.params.receiver
  var query = `receiver=${receiver}`
  request(`${BASE_URL}/generate-chat-message?${query}`,
    function (error, response, body) {
      if (error) {
        console.log('ERROR with user request!')
        return res.sendStatus(response.statusCode)
      }
      body = JSON.parse(body)
      res.send(body.msg)
  })
})

app.listen(3000, function () {
  console.log('Express app listening port 3000')
})
