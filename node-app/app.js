const express = require('express')
const request = require('request')
const qs      = require('querystring')

const BASE_URL  = 'http://localhost:8080'
const NODE_PORT = 3000

const app = express()

app.get('/hello', (req, res) => {
  res.send('Hello World!')
})

app.get('/message/:receiver', (req, res, next) => {
  const query = qs.stringify(req.params)

  request(`${BASE_URL}/generate-chat-message?${query}`,
    (error, response, body) => {
      if (error) {
        console.log('ERROR with user request!')
        return res.sendStatus(response.statusCode)
      }
      body = JSON.parse(body)
      res.send(body.msg)
  })
})

app.listen(NODE_PORT, () => {
  console.log(`Express app listening port ${NODE_PORT}...`)
})
