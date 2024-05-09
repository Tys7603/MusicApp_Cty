
const model = require('../models/musicVideoModel.js')

const getListMusicVideoController = async (req, res) => {
    try {

        const musicVideos = await model.getListMusicVideo()

        res.json({ status: 200, musicVideos })

    } catch (error) {
        res.json({ status: "400", message: error.message });
    }

}

module.exports = {
    getListMusicVideoController
}
