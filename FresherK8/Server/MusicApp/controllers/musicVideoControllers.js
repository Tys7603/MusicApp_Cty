
const model = require('../models/musicVideoModel.js')

const getListMusicVideoController = async (req, res) => {
    try {

        const musicVideos = await model.getListMusicVideo()

        res.json({ status: 200, musicVideos })

    } catch (error) {
        res.json({ status: "400", message: error.message });
    }

}

const getListMusicVideoExcludingIdController = async (req, res) => {
    try {

        const { musicVideoId } = req.params

        const musicVideos = await model.getListMusicVideoExcludingId(musicVideoId)

        res.json({ status: 200, musicVideos })

    } catch (error) {
        res.json({ status: "400", message: error.message });
    }

}

module.exports = {
    getListMusicVideoController,
    getListMusicVideoExcludingIdController
}
