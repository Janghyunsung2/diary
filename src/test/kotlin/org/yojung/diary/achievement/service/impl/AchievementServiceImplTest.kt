package org.yojung.diary.achievement.service.impl

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.web.multipart.MultipartFile
import org.yojung.diary.achievement.domain.Achievement
import org.yojung.diary.achievement.dto.AchievementRegisterRequest
import org.yojung.diary.achievement.dto.AchievementResponse
import org.yojung.diary.achievement.repository.AchievementRepository
import org.yojung.diary.storage.ObjectStorage
import java.time.ZonedDateTime

class AchievementServiceImplTest : BehaviorSpec( {

    var achievementRepository = mockk<AchievementRepository>()
    var achievementMapper = mockk<org.yojung.diary.achievement.mapper.AchievementMapper>()
    var objectStorage = mockk<ObjectStorage>()
    var achievementService = AchievementServiceImpl(achievementRepository,
        achievementMapper, objectStorage)


    afterTest {
        clearMocks(achievementRepository, achievementMapper, objectStorage)
    }

    given("업적 등록 서비스가 주어졌을 때") {
        `when`("업적 서비스가 등록될 때") {
            then("업적이 성공적으로 등록되어야 한다") {
                val image = mockk<MultipartFile>()
                val request = AchievementRegisterRequest(
                    code = "testCode",
                    nextCode = "nextCode",
                    name = "Test Achievement",
                    description = "This is a test achievement",
                    creditReward = 10,
                    progressValue = 1,
                    image = image
                )
                val response = AchievementResponse(
                    code = "testCode",
                    nextCode = "nextCode",
                    name = "Test Achievement",
                    description = "This is a test achievement",
                    creditReward = 10,
                    progressValue = 1,
                    iconUrl = "http://example.com/image.jpg",
                    createdAt = ZonedDateTime.now()
                )

                val achievement = Achievement(
                    code = request.code ?: "testCode",
                    nextCode = request.nextCode,
                    name = request.name,
                    description = request.description,
                    creditReward = request.creditReward,
                    iconUrl = response.iconUrl ?: "http://example.com/image.jpg",
                    progressValue = request.progressValue
                )
                every { objectStorage.uploadFile(any()) } returns achievement.iconUrl
                every { achievementMapper.toEntity(any()) } returns achievement
                every { achievementMapper.toResponse(any()) } returns response
                every { achievementRepository.save<Achievement>(any()) } returns achievement

                val achievementResponse = achievementService.registerAchievement(request)

                assertNotNull(achievementResponse)
                assertEquals(response.code, achievementResponse.code)
                assertEquals(response.nextCode, achievementResponse.nextCode)
                assertEquals(response.name, achievementResponse.name)
                assertEquals(response.description, achievementResponse.description)
                assertEquals(response.creditReward, achievementResponse.creditReward)
                assertEquals(response.progressValue, achievementResponse.progressValue)
                assertEquals(response.iconUrl, achievementResponse.iconUrl)
            }
        }
    }

    given("업적 수정 서비스가 주어졌을 때") {
        `when`("업적 서비스가 수정될 때") {
            then("업적이 성공적으로 수정되어야 한다") {
                val image = mockk<MultipartFile>()
                val request = org.yojung.diary.achievement.dto.AchievementUpdateRequest(
                    code = "testCode",
                    name = "Updated Achievement",
                    nextCode = "nextCode",
                    description = "This is an updated achievement",
                    creditReward = 20,
                    progressValue = 2,
                    image = image
                )
                val response = AchievementResponse(
                    code = "testCode",
                    nextCode = request.nextCode,
                    name = request.name,
                    description = request.description,
                    creditReward = request.creditReward,
                    progressValue = request.progressValue,
                    iconUrl = "http://example.com/image.jpg",
                    createdAt = ZonedDateTime.now()
                )

                val achievement = Achievement(
                    code = "testCode",
                    nextCode = request.nextCode,
                    name = request.name,
                    description = request.description,
                    creditReward = request.creditReward,
                    iconUrl = response.iconUrl ?: "http://example.com/image.jpg",
                    progressValue = request.progressValue
                )
                every { objectStorage.uploadFile(any()) } returns response.iconUrl
                every { achievementMapper.toEntity(any()) } returns achievement
                every { achievementMapper.toResponse(any()) } returns response
                every { achievementRepository.findById("testCode") } returns java.util.Optional.of(achievement)
                every { achievementRepository.save<Achievement>(any()) } returns achievement

                val achievementResponse = achievementService.updateAchievement("testCode", request)

                assertNotNull(achievementResponse)
                assertEquals(response.code, achievementResponse.code)
                assertEquals(response.nextCode, achievementResponse.nextCode)
                assertEquals(response.name, achievementResponse.name)
                assertEquals(response.description, achievementResponse.description)
                assertEquals(response.creditReward, achievementResponse.creditReward)
                assertEquals(response.progressValue, achievementResponse.progressValue)
                assertEquals(response.iconUrl, achievementResponse.iconUrl)
            }
        }
    }

    given("업적 조회 서비스가 주어졌을 때") {
        `when`("업적 서비스가 조회될 때") {
            then("업적이 성공적으로 조회되어야 한다") {
                val achievement = Achievement(
                    code = "testCode",
                    nextCode = "nextCode",
                    name = "Test Achievement",
                    description = "This is a test achievement",
                    creditReward = 10,
                    iconUrl = "http://example.com/image.jpg",
                    progressValue = 1
                )
                val response = AchievementResponse(
                    code = achievement.code,
                    nextCode = achievement.nextCode,
                    name = achievement.name,
                    description = achievement.description,
                    creditReward = achievement.creditReward,
                    progressValue = achievement.progressValue,
                    iconUrl = achievement.iconUrl,
                    createdAt = ZonedDateTime.now()
                )

                every { achievementRepository.findById("testCode") } returns java.util.Optional.of(achievement)
                every { achievementMapper.toResponse(any()) } returns response

                val achievementResponse = achievementService.getAchievement("testCode")

                assertNotNull(achievementResponse)
                assertEquals(response.code, achievementResponse.code)
                assertEquals(response.nextCode, achievementResponse.nextCode)
                assertEquals(response.name, achievementResponse.name)
                assertEquals(response.description, achievementResponse.description)
                assertEquals(response.creditReward, achievementResponse.creditReward)
                assertEquals(response.progressValue, achievementResponse.progressValue)
                assertEquals(response.iconUrl, achievementResponse.iconUrl)
            }
        }
    }

    given("업적 페이징 조회 서비스가 주어졌을 때") {
        `when`("업적 페이징 서비스가 조회될 때") {
            then("업적 페이징이 성공적으로 조회되어야 한다.") {
                val pageable: Pageable = PageRequest.of(0, 10)
                val achievement = mockk<Achievement>()
                val response = AchievementResponse(
                    code = "code1",
                    nextCode = "code2",
                    name = "Test Achievement",
                    description = "Desc",
                    creditReward = 100,
                    progressValue = 1,
                    iconUrl = "http://example.com/icon.jpg",
                    createdAt = ZonedDateTime.now()
                )

                // stub
                every { achievementRepository.findAll(pageable) } returns PageImpl(listOf(achievement))
                every { achievementMapper.toResponse(achievement) } returns response

                val result = achievementService.getAchievements(pageable)

                result.totalElements shouldBe 1
                result.content.first() shouldBe response

                verify { achievementRepository.findAll(pageable) }
                verify { achievementMapper.toResponse(achievement) }
            }
        }
    }

    given("업적 삭제 서비스가 주어졌을 때") {
        `when`("업적 삭제가 호출될 때") {
            then("레포지토리의 deleteAchievementByCode가 호출되어야 한다.") {
                val code = "testCode"

                every { achievementRepository.deleteAchievementByCode(code) } just Runs

                achievementService.deleteAchievement(code)

                verify { achievementRepository.deleteAchievementByCode(code) }
            }
        }
    }
})