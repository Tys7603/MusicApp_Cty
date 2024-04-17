const model = require('../models/songAgainModel.js')

const getListSongAgainController = async (req, res) => {
    try {
        const {id} = req.params
        const songAgain = await model.getListSongAgain(id)
        res.json({status : 200 , songAgain})
    } catch (error) {
        res.json({status : 400 , error})
    }
}

module.exports = {
    getListSongAgainController
}