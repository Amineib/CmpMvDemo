package com.naar.nmovies.data

import com.naar.nmovies.data.remote.responses.*
import com.naar.nmovies.domain.models.*

fun SpokenLanguageResponse.toDomain(): SpokenLanguage? {
    return safeParse {
        SpokenLanguage(
            english_name = requiredField("english_name", english_name),
            iso_639_1 = requiredField("iso_639_1", iso_639_1),
            name = requiredField("name", name),
        )
    }
}


fun ProductionCountryResponse.toDomain(): ProductionCountry? {
    return safeParse {
        ProductionCountry(
            iso_3166_1 = requiredField("iso_3166_1", iso_3166_1),
            name = requiredField("name", name),
        )
    }
}

/*fun GenreResponse.toDomain(): Genre? {
    return safeParse {
        Genre(
           id, name = name
        )
    }
}*/

fun ProductionCompanyResponse.toDomain(): ProductionCompany? {
    return safeParse {
        ProductionCompany(
            id = requiredField("id", id),
            logo_path = logo_path,
            name = requiredField("name", name),
            origin_country = origin_country
        )
    }
}

fun NetworkResponse.toDomain(): Network? {
    return safeParse {
        Network(
            id = requiredField("id", id),
            logo_path = logo_path,
            name = requiredField("name", name),
            origin_country = origin_country
        )
    }
}

fun CreatedByResponse.toDomain(): CreatedBy? {
    return safeParse {
        CreatedBy(
            credit_id = requiredField("credit_id", credit_id),
            gender = gender.toGenderDomain(),
            id = requiredField("id", id),
            name = requiredField("name", name),
            profile_path = profile_path
        )
    }
}

fun EpisodeResponse.toDomain(): Episode? {
    return safeParse {
        Episode(
            air_date = air_date,
            crew = crew?.mapNotNull { it.toDomain() }.orEmpty(),
            episode_number = episode_number,
            guest_stars = guest_stars?.mapNotNull { it.toDomain() }.orEmpty(),
            id = requiredField("id", id),
            name = requiredField("name", name),
            overview = overview,
            production_code = production_code,
            season_number = season_number,
            still_path = still_path,
            vote_average = vote_average,
            vote_count = vote_count,
        )
    }
}

private fun GuestStarResponse.toDomain(): GuestStar? {
    return safeParse {
        GuestStar(
            adult = adult,
            character = character,
            credit_id = credit_id,
            gender = gender.toGenderDomain(),
            id = requiredField("id", id),
            name = requiredField("name", name),
            known_for_department = known_for_department,
            order = order,
            original_name = original_name,
            popularity = popularity,
            profile_path = profile_path
        )
    }
}


private fun CrewResponse.toDomain(): Crew? {
    return safeParse {
        Crew(
            adult = adult,
            credit_id = credit_id,
            gender = gender.toGenderDomain(),
            id = requiredField("id", id),
            name = requiredField("name", name),
            known_for_department = known_for_department,
            original_name = original_name,
            popularity = popularity,
            profile_path = profile_path,
            department = department,
            job = job
        )
    }
}


fun BelongsToCollectionResponse.toDomain(): BelongsToCollection? {
    return safeParse {
        BelongsToCollection(
            backdrop_path, id, name, poster_path
        )
    }
}




fun Int?.toGenderDomain(): Gender {
    return when (this) {
        null -> Gender.UNKNOWN
        //FixMe : add the true mapping
        else -> Gender.MALE
    }
}