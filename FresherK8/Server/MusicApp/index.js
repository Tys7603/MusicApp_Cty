var express = require('express')
var app = express()
var bodyParser = require('body-parser')
const server = require('http').createServer(app);

app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())

// Hiển thị thông tin HTTP khi yêu cầu
app.use((req, res, next) => {
  console.log("🚀 ~ file: index.js:59 ~ app.use ~ req:", req.method + req.url);
  next();
});
//

var playListRouter = require('../MusicApp/routers/playlistRoter.js')
var topicAndCategoryRouter = require('../MusicApp/routers/topicAndCategoryRouter.js')
// routers
app.use("/api", playListRouter)
app.use("/api", topicAndCategoryRouter)
const port = 3000 || process.env.DB_PORT;
server.listen(port, () => {
  console.log(`Example app listening on port ${port}`);
});