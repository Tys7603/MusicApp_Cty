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
var topicRouter = require('./routers/topicRouter.js')
var categoryRouter = require('./routers/categoryRouter.js')
var songAgainRouter = require('./routers/songAgainRouter.js')
var albumRouter = require('./routers/albumRouter.js')
var songRouter = require('./routers/songRouter.js')
var songRankRouter = require('./routers/songRankRouter.js')
var userRouter = require('./routers/userRouter.js')

// routers
app.use("/api", playListRouter)
app.use("/api", topicRouter)
app.use("/api", categoryRouter)
app.use("/api", songAgainRouter)
app.use("/api", albumRouter)
app.use("/api", songRouter)
app.use("/api", songRankRouter)
app.use("/api", userRouter)

const port = 3000 || process.env.DB_PORT;
server.listen(port, () => {
  console.log(`Example app listening on port ${port}`);
});