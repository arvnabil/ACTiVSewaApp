package com.activ.activsewa.network


import com.activ.activsewa.model.response.Wrapper
import com.activ.activsewa.model.response.brand.BrandResponse
import com.activ.activsewa.model.response.customer.CustomerRepsonse
import com.activ.activsewa.model.response.daftar.DaftarResponse
import com.activ.activsewa.model.response.home.HomeResponse
import com.activ.activsewa.model.response.login.LoginResponse
import com.activ.activsewa.model.response.payment.PaymentResponse
import com.activ.activsewa.model.response.pengajuan.PengajuanResponse
import com.activ.activsewa.model.response.set_verification.SetVerificationResponse
import com.activ.activsewa.model.response.setujui_verifikasi.SetujuiVerifikasiResponse
import com.activ.activsewa.model.response.tambahBrand.TambahBrandResponse
import com.activ.activsewa.model.response.tambah_pengajuan.TambahPengajuanResponse
import com.activ.activsewa.model.response.tambah_produk.TambahProdukResponse
import com.activ.activsewa.model.response.tolak_verifikasi.TolakVerifikasiResponse
import com.activ.activsewa.model.response.transaction.TransactionResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*
interface Endpoint {

    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email") email:String,
              @Field("password") password:String) : Observable<Wrapper<LoginResponse>>

    @FormUrlEncoded
    @POST("register")
    fun register(@Field("name") name:String,
                 @Field("email") email:String,
                 @Field("password") password:String,
                 @Field("telp") telp:String) : Observable<Wrapper<DaftarResponse>>


    @POST("product")
    fun tambahProduk(@Header("Authorization") token:String,
                     @Body body: MultipartBody) : Observable<Wrapper<TambahProdukResponse>>

    @FormUrlEncoded
    @POST("brand")
    fun addBrand(@Header("Authorization") token:String,
                  @Field("name") name:String) : Observable<Wrapper<TambahBrandResponse>>

    @FormUrlEncoded
    @POST("filing")
    fun filingAdd(@Header("Authorization") token:String,
                  @Field("product_id") product_id:String) : Observable<Wrapper<TambahPengajuanResponse>>

    @DELETE("filing/{id}")
    fun filingDelete(@Header("Authorization") token: String,@Path("id") id: String) : Observable<Wrapper<PengajuanResponse>>

    @FormUrlEncoded
    @POST("filing/verification")
    fun filingSetVerification(
        @Header("Authorization") token:String,
        @Field("nama_penyewa") nama_penyewa:String,
        @Field("nik") nik:String,
        @Field("npwp") npwp:String,
        @Field("telp") telp:String,
        @Field("alamat") alamat:String) : Observable<Wrapper<SetVerificationResponse>>

    @FormUrlEncoded
    @PUT("payment/{id}")
    fun payment(
        @Header("Authorization") token:String,
        @Path("id") id: String,
        @Field("tanggal_sewa") nama_penyewa:String,
        @Field("metode") npwp:String,) : Observable<Wrapper<PaymentResponse>>

    @FormUrlEncoded
    @PUT("transaction/{id}/setujui-verifikasi")
    fun setujuiVerifikasi(
        @Header("Authorization") token:String,
        @Path("id") id: String,
        @Field("cek_nama") cek_nama:String,
        @Field("cek_nik") cek_nik:String,
        @Field("cek_npwp") cek_npwp:String,
        @Field("cek_telp") cek_telp:String,
        @Field("cek_alamat") cek_alamat:String,
        @Field("cek_foto_ktp") cek_foto_ktp:String,
        @Field("cek_foto_npwp") cek_foto_npwp:String,) : Observable<Wrapper<SetujuiVerifikasiResponse>>

    @FormUrlEncoded
    @PUT("transaction/{id}/tolak-verifikasi")
    fun tolakVerifikasi(
        @Header("Authorization") token:String,
        @Path("id") id: String,
        @Field("cek_nama") cek_nama:String,
        @Field("cek_nik") cek_nik:String,
        @Field("cek_npwp") cek_npwp:String,
        @Field("cek_telp") cek_telp:String,
        @Field("cek_alamat") cek_alamat:String,
        @Field("cek_foto_ktp") cek_foto_ktp:String,
        @Field("cek_foto_npwp") cek_foto_npwp:String,) : Observable<Wrapper<TolakVerifikasiResponse>>

    @GET("brand")
    fun brand(@Header("Authorization") token:String) : Observable<Wrapper<BrandResponse>>

    @GET("product")
    fun home(@Header("Authorization") token:String) : Observable<Wrapper<HomeResponse>>

    @GET("user-customer")
    fun allCustomer(@Header("Authorization") token:String) : Observable<Wrapper<CustomerRepsonse>>

    @GET("filing")
    fun filing(@Header("Authorization") token:String) : Observable<Wrapper<PengajuanResponse>>

    @GET("transaction")
    fun transaction(@Header("Authorization") token:String) : Observable<Wrapper<TransactionResponse>>

    @GET("transaction/with-auth")
    fun transactionWA(@Header("Authorization") token:String) : Observable<Wrapper<TransactionResponse>>

}