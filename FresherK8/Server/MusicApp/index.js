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

var playListRouter = require('../MusicApp/routers/playlistRoter.js')
var topicRouter = require('./routers/topicRouter.js')
var categoryRouter = require('./routers/categoryRouter.js')
var songAgainRouter = require('./routers/songAgainRouter.js')
var albumRouter = require('./routers/albumRouter.js')
var songRouter = require('./routers/songRouter.js')
var songRankRouter = require('./routers/songRankRouter.js')
var userRouter = require('./routers/userRouter.js')
var musicVideoRouter = require('./routers/musicVideoRouter.js')
var lyricRouter = require('./routers/lyric.router.js')
var searchRouter = require('./routers/search.router.js')
var followRouter = require('./routers/follow_router.js')

// routers
app.use("/api", playListRouter)
app.use("/api", topicRouter)
app.use("/api", categoryRouter)
app.use("/api", songAgainRouter)
app.use("/api", albumRouter)
app.use("/api", songRouter)
app.use("/api", songRankRouter)
app.use("/api", userRouter)
app.use("/api", musicVideoRouter)
app.use("/api", lyricRouter)
app.use("/api", searchRouter)
app.use("/api", followRouter)

app.get("/music" , (req, res) => {
  res.send("Halo")
})

app.get("/.well-known/assetlinks.json", (req, res) => {
  res.json([{
    "relation": ["delegate_permission/common.handle_all_urls"],
    "target": {
      "namespace": "android_app",
      "package_name": "com.example.musicapp",
      "sha256_cert_fingerprints": [
        "8D:D1:06:52:C0:41:BF:4B:77:7E:C5:4B:F6:5E:34:F2:22:82:37:E4:C8:08:5F:3B:88:29:B7:27:D0:84:6F:77"
      ]
    }
  }]);
});


const port = 3000 || process.env.DB_PORT;
server.listen(port, () => {
  console.log(`Example app listening on port ${port}`);
});