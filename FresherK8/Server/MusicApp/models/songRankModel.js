
const queryDatabase = require("../database/database.js")

const getListSongRank = async () => {
    const query = "SELECT r.rank_name, s.song_name, s.song_image, s.song_url, a.name_artist " +
        "FROM song_rank sr " +
        "JOIN song s ON sr.song_id = s.song_id " +
        "JOIN album a ON s.album_id = a.album_id " +
        "JOIN rank r ON sr.song_rank = r.rank_id " +
        "ORDER BY s.song_listen DESC"

    return await queryDatabase(query);
};
module.exports = {
    getListSongRank
}