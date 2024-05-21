
const model = require('../models/lyric.model.js')

const getLyricBySongIdController = async (req, res) => {

  try {
    const {songId} = req.params
    const lyrics = await model.getLyricsBySongId(songId)

    res.json({ status: 200 , lyrics  })
  } catch (error) {
    res.json({ status: "400", message : error.message  });
  }

}

module.exports = {
  getLyricBySongIdController
}
