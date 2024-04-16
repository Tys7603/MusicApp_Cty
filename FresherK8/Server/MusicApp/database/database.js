require('dotenv').config();
const mysql = require('mysql');

const con = mysql.createPool({
    host: process.env.DB_HOST,
    user: process.env.DB_USER,
    port: process.env.DB_PORT,
    database: process.env.DB_NAME
});


// Truy xuất dữ liệu
const queryDatabase = (query, values) => {
    return new Promise((resolve, reject) => {
        con.query(query, values, (err, results, fields) => {
            if (err) {
                reject(err);
                return;
            } 
            resolve(results);
        });
    });
};

module.exports = queryDatabase
