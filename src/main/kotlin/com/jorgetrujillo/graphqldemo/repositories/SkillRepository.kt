package com.jorgetrujillo.graphqldemo.repositories

import com.jorgetrujillo.graphqldemo.domain.Skill
import org.springframework.data.mongodb.repository.MongoRepository

interface SkillRepository : MongoRepository<Skill, String>
