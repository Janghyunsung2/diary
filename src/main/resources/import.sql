-- 관리자 데이터
INSERT INTO admins (name, email, password) VALUES ('관리자', 'test@gmail.com', '$2a$12$b4gcKJum84B5s5lTpcxZ5OdGr5iWdSo0I/L61Muw5WLX22dZE2hDe');

-- 사용자 데이터 10개
INSERT INTO users (oauth_id, provider, nickname, password, gender, profile_image, created_at, last_login_at, is_active) VALUES ('user1@example.com', 'google', '사용자1', 'pw', 'MALE', 'https://example.com/profile1.jpg', NOW(), NOW(), true);
INSERT INTO users (oauth_id, provider, nickname, password, gender, profile_image, created_at, last_login_at, is_active) VALUES ('user2@example.com', 'google', '사용자2', 'pw', 'FEMALE', 'https://example.com/profile2.jpg', NOW(), NOW(), true);
INSERT INTO users (oauth_id, provider, nickname, password, gender, profile_image, created_at, last_login_at, is_active) VALUES ('user3@example.com', 'kakao', '사용자3', 'pw', 'UNKNOWN', 'https://example.com/profile3.jpg', NOW(), NOW(), true);
INSERT INTO users (oauth_id, provider, nickname, password, gender, profile_image, created_at, last_login_at, is_active) VALUES ('user4@example.com', 'naver', '사용자4', 'pw', 'MALE', 'https://example.com/profile4.jpg', NOW(), NOW(), true);
INSERT INTO users (oauth_id, provider, nickname, password, gender, profile_image, created_at, last_login_at, is_active) VALUES ('user5@example.com', 'google', '사용자5', 'pw', 'FEMALE', 'https://example.com/profile5.jpg', NOW(), NOW(), true);
INSERT INTO users (oauth_id, provider, nickname, password, gender, profile_image, created_at, last_login_at, is_active) VALUES ('user6@example.com', 'kakao', '사용자6', 'pw', 'MALE', 'https://example.com/profile6.jpg', NOW(), NOW(), true);
INSERT INTO users (oauth_id, provider, nickname, password, gender, profile_image, created_at, last_login_at, is_active) VALUES ('user7@example.com', 'naver', '사용자7', 'pw', 'FEMALE', 'https://example.com/profile7.jpg', NOW(), NOW(), true);
INSERT INTO users (oauth_id, provider, nickname, password, gender, profile_image, created_at, last_login_at, is_active) VALUES ('user8@example.com', 'google', '사용자8', 'pw', 'UNKNOWN', 'https://example.com/profile8.jpg', NOW(), NOW(), true);
INSERT INTO users (oauth_id, provider, nickname, password, gender, profile_image, created_at, last_login_at, is_active) VALUES ('user9@example.com', 'kakao', '사용자9', 'pw', 'MALE', 'https://example.com/profile9.jpg', NOW(), NOW(), true);
INSERT INTO users (oauth_id, provider, nickname, password, gender, profile_image, created_at, last_login_at, is_active) VALUES ('user10@example.com', 'naver', '사용자10', 'pw', 'FEMALE', 'https://example.com/profile10.jpg', NOW(), NOW(), true);

-- AI 모드 데이터 10개
INSERT INTO ai_modes (mode, label, description, image_url, prompt, color_label, color_bg, credit_amount) VALUES ('THERAPIST', '심리상담사', '심리적 치유와 상담을 제공하는 AI 모드', 'https://example.com/therapist.jpg', '당신의 감정을 이해하고 치유하는 따뜻한 상담사입니다.', '#ffffff', '#4CAF50', 10);
INSERT INTO ai_modes (mode, label, description, image_url, prompt, color_label, color_bg, credit_amount) VALUES ('FRIEND', '친한친구', '친근하고 따뜻한 친구처럼 대화하는 모드', 'https://example.com/friend.jpg', '오늘 하루는 어떠셨나요? 편하게 이야기해주세요.', '#ffffff', '#2196F3', 5);
INSERT INTO ai_modes (mode, label, description, image_url, prompt, color_label, color_bg, credit_amount) VALUES ('MENTOR', '멘토', '인생 조언과 방향성을 제시하는 멘토 모드', 'https://example.com/mentor.jpg', '당신의 성장을 위한 조언을 드리겠습니다.', '#ffffff', '#FF9800', 15);
INSERT INTO ai_modes (mode, label, description, image_url, prompt, color_label, color_bg, credit_amount) VALUES ('COACH', '코치', '목표 달성을 위한 동기부여와 코칭', 'https://example.com/coach.jpg', '목표를 향해 함께 달려가봅시다!', '#ffffff', '#F44336', 12);
INSERT INTO ai_modes (mode, label, description, image_url, prompt, color_label, color_bg, credit_amount) VALUES ('PHILOSOPHER', '철학자', '깊이 있는 사고와 철학적 관점 제공', 'https://example.com/philosopher.jpg', '삶의 의미에 대해 함께 생각해봅시다.', '#ffffff', '#9C27B0', 20);
INSERT INTO ai_modes (mode, label, description, image_url, prompt, color_label, color_bg, credit_amount) VALUES ('POET', '시인', '감성적이고 아름다운 표현으로 응답', 'https://example.com/poet.jpg', '당신의 마음을 시로 표현해드리겠습니다.', '#ffffff', '#E91E63', 8);
INSERT INTO ai_modes (mode, label, description, image_url, prompt, color_label, color_bg, credit_amount) VALUES ('ANALYST', '분석가', '객관적이고 논리적인 분석 제공', 'https://example.com/analyst.jpg', '상황을 객관적으로 분석해드리겠습니다.', '#ffffff', '#607D8B', 18);
INSERT INTO ai_modes (mode, label, description, image_url, prompt, color_label, color_bg, credit_amount) VALUES ('MOTIVATOR', '동기부여사', '긍정적 에너지와 동기부여 제공', 'https://example.com/motivator.jpg', '당신은 충분히 잘하고 있습니다!', '#ffffff', '#FFEB3B', 7);
INSERT INTO ai_modes (mode, label, description, image_url, prompt, color_label, color_bg, credit_amount) VALUES ('LISTENER', '경청자', '공감하며 들어주는 좋은 청취자', 'https://example.com/listener.jpg', '무엇이든 편하게 말씀해주세요.', '#ffffff', '#795548', 3);
INSERT INTO ai_modes (mode, label, description, image_url, prompt, color_label, color_bg, credit_amount) VALUES ('ADVISOR', '조언자', '실용적인 조언과 해결책 제시', 'https://example.com/advisor.jpg', '실용적인 해결책을 함께 찾아보겠습니다.', '#ffffff', '#009688', 10);

-- 업적 데이터 10개
INSERT INTO achievements (code, next_code, name, description, credit_reward, icon_url, progress_value, created_at) VALUES ('FIRST_DIARY', 'DIARY_WEEK', '첫 일기', '첫 번째 일기를 작성하세요', 10, 'https://example.com/first_diary.png', 1, NOW());
INSERT INTO achievements (code, next_code, name, description, credit_reward, icon_url, progress_value, created_at) VALUES ('DIARY_WEEK', 'DIARY_MONTH', '일주일 일기', '7일 연속 일기를 작성하세요', 50, 'https://example.com/week_diary.png', 7, NOW());
INSERT INTO achievements (code, next_code, name, description, credit_reward, icon_url, progress_value, created_at) VALUES ('DIARY_MONTH', 'DIARY_100', '한달 일기', '30일 연속 일기를 작성하세요', 200, 'https://example.com/month_diary.png', 30, NOW());
INSERT INTO achievements (code, next_code, name, description, credit_reward, icon_url, progress_value, created_at) VALUES ('DIARY_100', null, '백일 일기', '100개의 일기를 작성하세요', 500, 'https://example.com/100_diary.png', 100, NOW());
INSERT INTO achievements (code, next_code, name, description, credit_reward, icon_url, progress_value, created_at) VALUES ('AI_FRIEND', 'AI_MASTER', 'AI 친구', 'AI와 첫 대화를 나누세요', 20, 'https://example.com/ai_friend.png', 1, NOW());
INSERT INTO achievements (code, next_code, name, description, credit_reward, icon_url, progress_value, created_at) VALUES ('AI_MASTER', null, 'AI 마스터', 'AI와 100번 대화하세요', 300, 'https://example.com/ai_master.png', 100, NOW());
INSERT INTO achievements (code, next_code, name, description, credit_reward, icon_url, progress_value, created_at) VALUES ('EMOTION_TRACKER', null, '감정 추적자', '10가지 다른 감정으로 일기를 작성하세요', 100, 'https://example.com/emotion.png', 10, NOW());
INSERT INTO achievements (code, next_code, name, description, credit_reward, icon_url, progress_value, created_at) VALUES ('MORNING_WRITER', null, '아침 작가', '오전 6시-9시에 일기를 작성하세요', 30, 'https://example.com/morning.png', 10, NOW());
INSERT INTO achievements (code, next_code, name, description, credit_reward, icon_url, progress_value, created_at) VALUES ('NIGHT_OWL', null, '올빼미', '밤 12시 이후에 일기를 작성하세요', 25, 'https://example.com/night.png', 5, NOW());
INSERT INTO achievements (code, next_code, name, description, credit_reward, icon_url, progress_value, created_at) VALUES ('PHILOSOPHER', null, '철학자', '철학자 모드로 10번 대화하세요', 80, 'https://example.com/philosopher_achievement.png', 10, NOW());

-- 일기 데이터 10개
INSERT INTO dailies (content, emotion_type, user_id, created_at, updated_at, visibility, deleted) VALUES ('오늘은 정말 좋은 하루였다.', 'HAPPY', 1, NOW(), NOW(), true, false);
INSERT INTO dailies (content, emotion_type, user_id, created_at, updated_at, visibility, deleted) VALUES ('힘든 일이 있었지만 괜찮다.', 'SAD', 2, NOW(), NOW(), true, false);
INSERT INTO dailies (content, emotion_type, user_id, created_at, updated_at, visibility, deleted) VALUES ('코틀린 배우는 중', 'EXCITED', 3, NOW(), NOW(), true, false);
INSERT INTO dailies (content, emotion_type, user_id, created_at, updated_at, visibility, deleted) VALUES ('맛있는 음식 먹음', 'GRATEFUL', 4, NOW(), NOW(), true, false);
INSERT INTO dailies (content, emotion_type, user_id, created_at, updated_at, visibility, deleted) VALUES ('운동 일주일차', 'PROUD', 5, NOW(), NOW(), true, false);
INSERT INTO dailies (content, emotion_type, user_id, created_at, updated_at, visibility, deleted) VALUES ('비와서 우울', 'MELANCHOLY', 6, NOW(), NOW(), true, false);
INSERT INTO dailies (content, emotion_type, user_id, created_at, updated_at, visibility, deleted) VALUES ('새로운 도전 앞둠', 'NERVOUS', 7, NOW(), NOW(), true, false);
INSERT INTO dailies (content, emotion_type, user_id, created_at, updated_at, visibility, deleted) VALUES ('하루종일 쉼', 'PEACEFUL', 8, NOW(), NOW(), true, false);
INSERT INTO dailies (content, emotion_type, user_id, created_at, updated_at, visibility, deleted) VALUES ('좋은 소식 들음', 'SURPRISED', 9, NOW(), NOW(), true, false);
INSERT INTO dailies (content, emotion_type, user_id, created_at, updated_at, visibility, deleted) VALUES ('책 읽음', 'THOUGHTFUL', 10, NOW(), NOW(), true, false);

-- 피드백 데이터 10개
INSERT INTO feedbacks (content, mode, daily_id, created_at) VALUES ('긍정적인 에너지가 느껴집니다.', 'THERAPIST', 1, NOW());
INSERT INTO feedbacks (content, mode, daily_id, created_at) VALUES ('희망을 잃지 않는 모습이 멋집니다.', 'FRIEND', 2, NOW());
INSERT INTO feedbacks (content, mode, daily_id, created_at) VALUES ('새로운 기술에 대한 열정이 좋습니다.', 'MENTOR', 3, NOW());
INSERT INTO feedbacks (content, mode, daily_id, created_at) VALUES ('감사하는 마음이 행복의 시작입니다.', 'PHILOSOPHER', 4, NOW());
INSERT INTO feedbacks (content, mode, daily_id, created_at) VALUES ('꾸준함의 변화를 경험 중이군요.', 'COACH', 5, NOW());
INSERT INTO feedbacks (content, mode, daily_id, created_at) VALUES ('비 오는 날의 우울도 의미가 있습니다.', 'POET', 6, NOW());
INSERT INTO feedbacks (content, mode, daily_id, created_at) VALUES ('긴장감은 성장의 신호입니다.', 'ANALYST', 7, NOW());
INSERT INTO feedbacks (content, mode, daily_id, created_at) VALUES ('휴식도 생산성의 일부입니다.', 'ADVISOR', 8, NOW());
INSERT INTO feedbacks (content, mode, daily_id, created_at) VALUES ('예상치 못한 기쁨이 왔군요!', 'MOTIVATOR', 9, NOW());
INSERT INTO feedbacks (content, mode, daily_id, created_at) VALUES ('독서를 통한 성찰이 멋집니다.', 'LISTENER', 10, NOW());

-- 크레딧 트랜잭션 데이터 10개
INSERT INTO credit_transactions (user_id, amount, reason, created_at) VALUES (1, 100, 'EARN',  NOW());
INSERT INTO credit_transactions (user_id, amount, reason, created_at) VALUES (1, -10, 'USE', NOW());
INSERT INTO credit_transactions (user_id, amount, reason, created_at) VALUES (2, 100, 'EARN',  NOW());
INSERT INTO credit_transactions (user_id, amount, reason, created_at) VALUES (2, 50, 'EARN',  NOW());
INSERT INTO credit_transactions (user_id, amount, reason, created_at) VALUES (3, 100, 'EARN',  NOW());
INSERT INTO credit_transactions (user_id, amount, reason, created_at) VALUES (3, -5, 'USE',  NOW());
INSERT INTO credit_transactions (user_id, amount, reason, created_at) VALUES (4, 100, 'EARN',  NOW());
INSERT INTO credit_transactions (user_id, amount, reason, created_at) VALUES (4, 30, 'EARN',  NOW());
INSERT INTO credit_transactions (user_id, amount, reason, created_at) VALUES (5, 100, 'EARN',  NOW());
INSERT INTO credit_transactions (user_id, amount, reason, created_at) VALUES (5, -15, 'USE',  NOW());

-- 사용자 업적 데이터 5개
INSERT INTO user_achievements (user_id, achievement_code, current_value, goal_value, completed, created_at) VALUES (1, 'FIRST_DIARY', 1, 1, true, NOW());
INSERT INTO user_achievements (user_id, achievement_code, current_value, goal_value, completed, created_at) VALUES (1, 'DIARY_WEEK', 3, 7, false, NOW());
INSERT INTO user_achievements (user_id, achievement_code, current_value, goal_value, completed, created_at) VALUES (2, 'FIRST_DIARY', 1, 1, true, NOW());
INSERT INTO user_achievements (user_id, achievement_code, current_value, goal_value, completed, created_at) VALUES (2, 'AI_FRIEND', 1, 1, true, NOW());
INSERT INTO user_achievements (user_id, achievement_code, current_value, goal_value, completed, created_at) VALUES (3, 'EMOTION_TRACKER', 3, 10, false, NOW());