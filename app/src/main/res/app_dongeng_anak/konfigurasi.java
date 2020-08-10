package com.example.namasteh.app_dongeng_anak;

public class konfigurasi {

    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //Pada tutorial Kali ini, karena kita membuat localhost maka alamatnya tertuju ke IP komputer
    //dimana File PHP tersebut berada
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
    public static final String URL_ADD_AKUN="http://192.168.43.152/appdongeng/tambahakun.php";
    public static final String URL_ISI_FEEDBACK="http://192.168.43.152/appdongeng/tambahfeedback.php";
    public static final String URL_REQUEST_CERITA="http://192.168.43.152/appdongeng/tambahrequestcerita.php";
    public static final String URL_POSTING_CERITA="http://192.168.43.152/appdongeng/tambahpostingcerita.php";
    public static final String URL_TAMPIL_POSTING_CERITA="http://192.168.43.152/appdongeng/tampilpostingcerita.php";
    public static final String URL_TAMPIL_BACA_POSTING_CERITA="http://192.168.43.152/appdongeng/tampilbacapostingcerita.php?id=";
    public static final String URL_TAMPIL_CERITA_SAYA="http://192.168.43.152/appdongeng/tampilceritasaya.php?id=";
    public static final String URL_UPDATE_CERITA_SAYA="http://192.168.43.152/appdongeng/updateceritasaya.php";
    public static final String URL_TAMPIL_LIKE="http://192.168.43.152/appdongeng/tampillike.php?id=";
    public static final String URL_TAMPIL_AKUN="http://192.168.43.152/appdongeng/tampilakun.php?id=";
    public static final String URL_SUKA_POSTINGAN="http://192.168.43.152/appdongeng/tambahsukapostingan.php";
    public static final String URL_TAMPIL_REQUEST_CERITA="http://192.168.43.152/appdongeng/tampilrequestcerita.php?id=";
    public static final String URL_TAMPIL_REKOM_CERITA="http://192.168.43.152/appdongeng/tampilrekomendasicerita.php";
    public static final String URL_TAMPIL_TREND_CERITA="http://192.168.43.152/appdongeng/tampiltrend.php";
    public static final String URL_TAMPIL_GRAPH_LIKE="http://192.168.43.152/appdongeng/tampilgraph.php";
    public static final String URL_TAMPIL_TREND_CERITA_LENGKAP="http://192.168.43.152/appdongeng/tampiltrendlengkap.php?tipe_cerita=";
//    public static final String URL_ADD_PEMBACA="http://192.168.1.75/dongeng/tambahpembaca.php";
//    public static final String URL_GET_ALL_PENULIS = "http://192.168.1.75/dongeng/tampilpenulis.php";
//    public static final String URL_UPDATE_EMP = "http://192.168.1.9/Android/pegawai/updatePgw.php";
//    public static final String URL_DELETE_EMP = "http://192.168.1.9/Android/pegawai/hapusPgw.php?id=";

    //Dibawah ini nama kolom akun
    public static final String KEY_AKUN_ID = "id";
    public static final String KEY_AKUN_NAMA = "nama";
    public static final String KEY_AKUN_EMAIL = "email";
    public static final String KEY_AKUN_ALAMAT = "alamat";
    public static final String KEY_AKUN_JENIS_KELAMIN = "jenis_kelamin"; //desg itu variabel untuk posisi
    public static final String KEY_AKUN_USIA = "usia"; //salary itu variabel untuk gajih
    public static final String KEY_AKUN_PASSWORD = "password";
    public static final String KEY_AKUN_STATUS = "status";

    //Dibawah ini kolom feedback
    public static final String KEY_FEEDBACK_ID = "id";
    public static final String KEY_FEEDBACK_NAMA = "nama";
    public static final String KEY_FEEDBACK_ISI = "isi";
    public static final String KEY_FEEDBACK_TIPE_MASUKKAN = "tipe_masukkan";

    //Dibawah ini kolom Request cerita
    public static final String KEY_REQUEST_CERITA_ID = "id";
    public static final String KEY_REQUEST_CERITA_JUDUL_CERITA = "judul_cerita";
    public static final String KEY_REQUEST_CERITA_TIPE_CERITA = "tipe_cerita";
    public static final String KEY_REQUEST_CERITA_STATUS = "status";

    //Dibawah ini nama kolom posting cerita
    public static final String KEY_POSTING_CERITA_ID = "id";
    public static final String KEY_POSTING_CERITA_TIPE_CERITA = "tipe_cerita";
    public static final String KEY_POSTING_CERITA_JUDUL= "judul";
    public static final String KEY_POSTING_CERITA_TGL_POSTING= "tgl_posting";
    public static final String KEY_POSTING_CERITA_ISI_CERITA= "isi_cerita"; //desg itu variabel untuk posisi
    public static final String KEY_POSTING_CERITA_STATUS= "status"; //salary itu variabel untuk gajih
    public static final String KEY_POSTING_CERITA_ID_POSTING= "id_posting"; //salary itu variabel untuk gajih

    //Dibawah ini kolom Rating(suka_posting)
    public static final String KEY_SUKA_POSTINGAN_ID = "id";
    public static final String KEY_SUKA_POSTINGAN_ID_POSTING = "id_posting";
    public static final String KEY_SUKA_POSTINGAN_SUKA = "suka";
    public static final String KEY_SUKA_POSTINGAN_ID_SUKA = "id_suka";
    public static final String KEY_SUKA_POSTINGAN_JUDUL_CERITA = "judul_cerita";

    //======================================================================================

    //JSON Tags daftar akun
    public static final String TAG_AKUN_JSON_ARRAY="akun";
    public static final String TAG_AKUN_ID = "id";
    public static final String TAG_AKUN_NAMA = "nama";
    public static final String TAG_AKUN_EMAIL = "email";
    public static final String TAG_AKUN_ALAMAT = "alamat";
    public static final String TAG_AKUN_JENIS_KELAMIN = "jenis_kelamin";
    public static final String TAG_AKUN_USIA = "usia";
    public static final String TAG_AKUN_PASSWORD = "password";

    //JSON Tags feedback
    public static final String TAG_FEEDBACK_JSON_ARRAY="feedback";
    public static final String TAG_FEEDBACK_ID = "id";
    public static final String TAG_FEEDBACK_NAMA = "nama";
    public static final String TAG_FEEDBACK_ISI = "isi";

    //JSON Tags Request
    public static final String TAG_REQUEST_CERITA_JSON_ARRAY="xrequest_cerita";
    public static final String TAG_REQUEST_CERITA_ID = "id";
    public static final String TAG_REQUEST_CERITA_JUDUL = "judul_cerita";
    public static final String TAG_REQUEST_CERITA_TIPE = "tipe_cerita";
    public static final String TAG_REQUEST_CERITA_STATUS = "status";
    public static final String TAG_REQUEST_CERITA_ID_REQUEST = "id_request";

    //JSON Tags Like
    public static final String TAG_REQUEST_LIKE_JSON_ARRAY="like";
    public static final String TAG_LIKE_ID = "id";
    public static final String TAG_LIKE_JUDUL_CERITA = "judul_cerita";
    public static final String TAG_LIKE_SUKA = "suka";
    public static final String TAG_LIKE_ID_SUKA = "id_suka";
    public static final String TAG_LIKE_TAHUN = "tahun";
    public static final String TAG_LIKE_ID_POSTING = "id_posting";

    //JSON Tags posting cerita
    public static final String TAG_POSTING_CERITA_JSON_ARRAY="posting_cerita";
    public static final String TAG_POSTING_CERITA_ID = "id";
    public static final String TAG_POSTING_CERITA_ID_POSTING = "id_posting";
    public static final String TAG_POSTING_CERITA_TIPE_CERITA = "tipe_cerita";
    public static final String TAG_POSTING_CERITA_JUDUL= "judul";
    public static final String TAG_POSTING_CERITA_TGL_POSTING= "tgl_posting";
    public static final String TAG_POSTING_CERITA_ISI_CERITA= "isi_cerita";
    public static final String TAG_POSTING_CERITA_STATUS= "status";
    public static final String TAG_POSTING_CERITA_TAHUN= "tahun";

    //JSON Tags posting cerita
    public static final String TAG_POSTING_REKOM_JSON_ARRAY="rekom_cerita";
    public static final String TAG_SUKA_POSTING_ID_POSTING = "id_posting";
    public static final String TAG_SUKA_POSTING_ID = "id";
    public static final String TAG_SUKA_POSTING_JUDUL_CERITA = "judul_cerita";
    public static final String TAG_SUKA_POSTING_SUKA = "suka";
    public static final String TAG_SUKA_POSTING_ID_SUKA = "id_suka";

    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String AKUN_ID = "id";
    public static final String CERITA_SAYA_ID = "id";
    public static final String POSTING_CERITA_ID = "id_posting";
    public static final String LIKE_POSTING = "id";
    public static final String REQUEST_SAYA = "id";
    public static final String GRAPH_ID = "id";
    public static final String POSTING_CERITA_TIPE_CERITA = "tipe_cerita";
}
