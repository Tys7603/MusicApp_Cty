
const queryDatabase = require("../database/database.js")

const getListMusicVideo = async () => {
    const query = "SELECT mv.music_video_id , mv.music_video_name, ar.artist_name, ar.artist_image, c.category_id " +
        "FROM Music_Video as mv " +
        "INNER JOIN Artist as ar ON mv.artist_id = ar.artist_id " +
        "INNER JOIN Category as c ON mv.category_id = c.category_id"
    return await queryDatabase(query)
}


module.exports = {
    getListMusicVideo
}

