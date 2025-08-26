package com.example.act8.repositori

import com.example.act8.room.Siswa
import com.example.act8.room.SiswaDao
import kotlinx.coroutines.flow.Flow

interface RepositoriSiswa {
    fun getAllSiswaStream(): Flow<List<Siswa>>

    suspend fun insertSiswa(siswa: Siswa)
    fun getSiswaStream(id: Int): Flow<Siswa?>
    suspend fun updateSiswa(siswa: Siswa)
    suspend fun deleteSiswa(siswa: Siswa)
}

class OfflineRepositoriSiswa(private val siswaDao: SiswaDao) : RepositoriSiswa {
    override fun getAllSiswaStream(): Flow<List<Siswa>> = siswaDao.getAllSiswa()
    override suspend fun insertSiswa(siswa: Siswa) = siswaDao.insert(siswa)
    override fun getSiswaStream(id: Int): Flow<Siswa?> = siswaDao.getSiswa(id)
    override suspend fun updateSiswa(siswa: Siswa) = siswaDao.update(siswa)
    override suspend fun deleteSiswa(siswa: Siswa) = siswaDao.delete(siswa)

}