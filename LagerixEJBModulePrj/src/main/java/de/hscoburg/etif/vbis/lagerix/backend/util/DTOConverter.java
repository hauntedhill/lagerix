package de.hscoburg.etif.vbis.lagerix.backend.util;

import de.hscoburg.etif.vbis.lagerix.backend.entity.Article;
import de.hscoburg.etif.vbis.lagerix.backend.entity.ArticleType;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Movement;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Movements;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleTypeDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.MovementDTO;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zuch1000
 */
public class DTOConverter {
    
    
    public static MovementDTO convert(Movement m)
    {
        MovementDTO dto = new MovementDTO();
           
        dto.setArticleID(m.getArticle().getId());
        dto.setTimestamp(m.getTime().getTime());
        dto.setBookedIn(Movements.INCORPORATE.equals(m.getMovement()));
           
        return dto;
           
    }
    
    public static List<MovementDTO> convertMovement(List<Movement> m)
    {
        List<MovementDTO> result = new ArrayList<MovementDTO>();
        
        if(m!=null)
        {
            for(Movement mm : m)
            {
                result.add(convert(mm));
            }
        }
        
        return result;
    }
    
    
    public static ArticleTypeDTO convert(ArticleType at)
    {
        ArticleTypeDTO dto = new ArticleTypeDTO();
        
        dto.setId(at.getId());
        dto.setDescription(at.getDescription());
        dto.setName(at.getName());
        dto.setStorageID(at.getStorage()!=null?at.getStorage().getId():0);
        dto.setMinimumStock(at.getMinimumStock());
        
        return dto;
    }
    
    public static List<ArticleTypeDTO> convertArticleType(List<ArticleType> m)
    {
        List<ArticleTypeDTO> result = new ArrayList<ArticleTypeDTO>();
        
        if(m!=null)
        {
            for(ArticleType mm : m)
            {
                result.add(convert(mm));
            }
        }
        
        return result;
    }
    
    public static ArticleDTO convert(Article a)
    {
        
        
        ArticleDTO dto = new ArticleDTO();
        
        dto.setArticleTypeID(a.getArticleType()!=null?a.getArticleType().getId():0);
        dto.setId(a.getId());
        dto.setYardID(a.getYard()!=null?a.getYard().getId():0);
        
        return dto;
    }
    
}
