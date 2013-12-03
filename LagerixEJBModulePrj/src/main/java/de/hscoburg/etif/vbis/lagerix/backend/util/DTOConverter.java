package de.hscoburg.etif.vbis.lagerix.backend.util;

import de.hscoburg.etif.vbis.lagerix.backend.entity.Article;
import de.hscoburg.etif.vbis.lagerix.backend.entity.ArticleType;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Groups;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Movement;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Movements;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Storage;
import de.hscoburg.etif.vbis.lagerix.backend.entity.User;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Yard;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.ArticleTypeDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.GroupDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.MovementDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.StorageDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.UserDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.YardDTO;
import de.hscoburg.etif.vbis.lagerix.backend.interfaces.dto.base.GroupType;
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
        public static YardDTO convert(Yard y)
        {
            YardDTO dto = new YardDTO();
        
        dto.setId(y.getId());
        dto.setStorageID(y.getStorage()!=null?y.getStorage().getId():0);
        
        
        return dto;
        }
    public static List<YardDTO> convertYard(List<Yard> m)
    {
        List<YardDTO> result = new ArrayList<YardDTO>();
        
        if(m!=null)
        {
            for(Yard mm : m)
            {
                result.add(convert(mm));
            }
        }
        
        return result;
    }
        
        public static StorageDTO convert(Storage s)
        {
            StorageDTO dto = new StorageDTO();
            
            dto.setId(s.getId());
            dto.setName(s.getName());
            List<YardDTO> yardResult = new ArrayList<YardDTO>();
            for(Yard y : s.getYards()!=null?s.getYards():new ArrayList<Yard>())
            {
                YardDTO yardDTO = new YardDTO();
                
                yardDTO.setId(y.getId());
                yardDTO.setStorageID(y.getStorage()!=null?y.getStorage().getId():0);
                yardResult.add(yardDTO);
            }
            dto.setYards(yardResult);
            return dto;
        }
        
        
        
        public static List<StorageDTO> convertStorage(List<Storage> m)
    {
        List<StorageDTO> result = new ArrayList<StorageDTO>();
        
        if(m!=null)
        {
            for(Storage mm : m)
            {
                result.add(convert(mm));
            }
        }
        
        return result;
    }
        
       
        
        
        public static UserDTO convert(User u)
        {
            UserDTO dto = new UserDTO();
            
            
        dto.setEmail(u.getEmail());
        dto.setFname(u.getFirstName());
        dto.setLname(u.getLastName());
            for(Groups g : u.getGroups()!=null?u.getGroups():new ArrayList<Groups>())
        {
            GroupDTO gDto = new GroupDTO();
            //gDto.setLagerId(g.get);
            gDto.setGroup(GroupType.valueOf(g.getGroups().name()));
            for(Storage s : g.getStorage()!=null?g.getStorage():new ArrayList<Storage>())
            {
                gDto.getStorageId().add(s.getId());
            }
            
            dto.getGroups().add(gDto);
        }
            return dto;
        }
        
        
         public static List<UserDTO> convertUser(List<User> m)
    {
        List<UserDTO> result = new ArrayList<UserDTO>();
        
        if(m!=null)
        {
            for(User mm : m)
            {
                result.add(convert(mm));
            }
        }
        
        return result;
    }
         
        
}
