
const queryDatabase = require("../database/database.js")

const getListMusicVideo = async () => {
    const query = "SELECT mv.music_video_id , mv.music_video_name, ar.artist_id, ar.artist_name, ar.artist_image, mv.music_video_image, mv.music_video_time, mv.music_video_proposal_new, t.topic_id " +
        "FROM Music_Video as mv " +
        "INNER JOIN Artist as ar ON mv.artist_id = ar.artist_id " +
        "INNER JOIN Topic as t ON mv.topic_id = t.topic_id"
    return await queryDatabase(query)
}


const getListMusicVideoExcludingId = async (musicVideoId) => {
    const query = "SELECT mv.music_video_id , mv.music_video_name, ar.artist_id, ar.artist_name, ar.artist_image, mv.music_video_image, mv.music_video_time, mv.music_video_proposal_new, t.topic_id " +
        "FROM Music_Video as mv " +
        "INNER JOIN Artist as ar ON mv.artist_id = ar.artist_id " +
        "INNER JOIN Topic as t ON mv.topic_id = t.topic_id " +
        "WHERE mv.music_video_id != ?"; // Sử dụng điều kiện WHERE để loại bỏ musicVideoId truyền vào
    return await queryDatabase(query, [musicVideoId]);
}


module.exports = {
    getListMusicVideo,
    getListMusicVideoExcludingId
}

