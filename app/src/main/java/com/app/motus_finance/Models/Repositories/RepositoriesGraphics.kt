package com.app.motus_finance.Models.Repositories

import com.app.motus_finance.Models.DAO.GraphicsDAO
import com.app.motus_finance.Models.DTO.GraphicsDTO
import com.app.motus_finance.Models.Entities.Graphics

class RepositoriesGraphics(private val dao: GraphicsDAO) {

    suspend fun insertGraphics(graphics: Graphics){
        return dao.insertGraphics(graphics)
    }
}