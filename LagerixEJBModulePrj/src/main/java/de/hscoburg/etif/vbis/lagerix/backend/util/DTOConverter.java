package de.hscoburg.etif.vbis.lagerix.backend.util;

import de.hscoburg.etif.vbis.lagerix.backend.entity.Article;
import de.hscoburg.etif.vbis.lagerix.backend.entity.ArticleType;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Groups;
import de.hscoburg.etif.vbis.lagerix.backend.entity.Movement;
import de.hscoburg.etif.vbis.lagerix.backend.entity.base.Movements;
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

/**
 * This class contains all converter logic for converting list an single Entities in there DTO equivalents.
 *
 * @author zuch1000
 */
public class DTOConverter {

    /**
     * Convert the movement to a movementDTO
     *
     * @param m - The movement class
     * @return The created movmentDTO class
     */
    public static MovementDTO convert(Movement m) {
        MovementDTO dto = new MovementDTO();

        dto.setArticleID(m.getArticle().getId());
        dto.setTimestamp(m.getTime().getTime());
        dto.setBookedIn(Movements.INCORPORATE.equals(m.getMovement()));

        return dto;

    }

    /**
     * Convert the movement list to a movementDTO list
     *
     * @param m - The movement list
     * @return The created movmentDTO list
     */
    public static List<MovementDTO> convertMovement(List<Movement> m) {
        List<MovementDTO> result = new ArrayList<MovementDTO>();

        if (m != null) {
            for (Movement mm : m) {
                result.add(convert(mm));
            }
        }

        return result;
    }

    /**
     * Convert the articleType to a articleTypeDTO
     *
     * @param at - The articleType class
     * @return The created articleTypeDTO class
     */
    public static ArticleTypeDTO convert(ArticleType at) {
        ArticleTypeDTO dto = new ArticleTypeDTO();

        dto.setId(at.getId());
        dto.setDescription(at.getDescription());
        dto.setName(at.getName());
        dto.setStorageID(at.getStorage() != null ? at.getStorage().getId() : 0);
        dto.setMinimumStock(at.getMinimumStock());

        return dto;
    }

    /**
     * Convert the articleType list to a articleTypeDTO list
     *
     * @param m - The articleType list
     * @return The created articleTypeDTO list
     */
    public static List<ArticleTypeDTO> convertArticleType(List<ArticleType> m) {
        List<ArticleTypeDTO> result = new ArrayList<ArticleTypeDTO>();

        if (m != null) {
            for (ArticleType mm : m) {
                result.add(convert(mm));
            }
        }

        return result;
    }

    /**
     * Convert the article list to a articleDTO list
     *
     * @param a - The article
     * @return The created articleDTO
     */
    public static ArticleDTO convert(Article a) {

        ArticleDTO dto = new ArticleDTO();

        dto.setArticleTypeID(a.getArticleType() != null ? a.getArticleType().getId() : 0);
        dto.setId(a.getId());
        dto.setYardID(a.getYard() != null ? a.getYard().getId() : 0);

        return dto;

    }

    /**
     * Convert the yard list to a yardDTO list
     *
     * @param y - The yard
     * @return The created yardDTO
     */
    public static YardDTO convert(Yard y) {
        YardDTO dto = new YardDTO();

        dto.setId(y.getId());
        dto.setStorageID(y.getStorage() != null ? y.getStorage().getId() : 0);

        return dto;
    }

    /**
     * Convert the yard list to a yardDTO list
     *
     * @param m - The yard list
     * @return The created yardDTO list
     */
    public static List<YardDTO> convertYard(List<Yard> m) {
        List<YardDTO> result = new ArrayList<YardDTO>();

        if (m != null) {
            for (Yard mm : m) {
                result.add(convert(mm));
            }
        }

        return result;
    }

    /**
     * Convert the storage to a storageDTO
     *
     * @param s - The storage
     * @return The created storageDTO
     */
    public static StorageDTO convert(Storage s) {
        StorageDTO dto = new StorageDTO();

        dto.setId(s.getId());
        dto.setName(s.getName());
        List<YardDTO> yardResult = new ArrayList<YardDTO>();
        for (Yard y : s.getYards() != null ? s.getYards() : new ArrayList<Yard>()) {
            YardDTO yardDTO = new YardDTO();

            yardDTO.setId(y.getId());
            yardDTO.setStorageID(y.getStorage() != null ? y.getStorage().getId() : 0);
            yardResult.add(yardDTO);
        }
        dto.setYards(yardResult);
        return dto;
    }

    /**
     * Convert the storage list to a storageDTO list
     *
     * @param m - The storage list
     * @return The created storageDTO list
     */
    public static List<StorageDTO> convertStorage(List<Storage> m) {
        List<StorageDTO> result = new ArrayList<StorageDTO>();

        if (m != null) {
            for (Storage mm : m) {
                result.add(convert(mm));
            }
        }

        return result;
    }

    /**
     * Convert the user to a userDTO
     *
     * @param u - The user
     * @return The created userDTO
     */
    public static UserDTO convert(User u) {
        UserDTO dto = new UserDTO();

        dto.setEmail(u.getEmail());
        dto.setFname(u.getFirstName());
        dto.setLname(u.getLastName());
        for (Groups g : u.getGroups() != null ? u.getGroups() : new ArrayList<Groups>()) {
            GroupDTO gDto = new GroupDTO();
            //gDto.setLagerId(g.get);
            gDto.setGroup(GroupType.valueOf(g.getGroups().name()));
            for (Storage s : g.getStorage() != null ? g.getStorage() : new ArrayList<Storage>()) {
                gDto.getStorageId().add(s.getId());
            }

            dto.getGroups().add(gDto);
        }
        return dto;
    }

    /**
     * Convert the user list to a userDTO list
     *
     * @param m - The user list
     * @return The created userDTO list
     */
    public static List<UserDTO> convertUser(List<User> m) {
        List<UserDTO> result = new ArrayList<UserDTO>();

        if (m != null) {
            for (User mm : m) {
                result.add(convert(mm));
            }
        }

        return result;
    }

    /**
     * Convert the article list to articleDTO list
     *
     * @param m - The article list
     * @return The created articleDTO list
     */
    public static List<ArticleDTO> convertArtice(List<Article> m) {
        List<ArticleDTO> result = new ArrayList<ArticleDTO>();

        if (m != null) {
            for (Article mm : m) {
                result.add(convert(mm));
            }
        }

        return result;
    }

}
