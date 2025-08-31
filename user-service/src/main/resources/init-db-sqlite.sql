-- Table structure for table `listings`
CREATE TABLE IF NOT EXISTS `users` (
     `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
     `name` TEXT NOT NULL,
     `created_at` INTEGER DEFAULT NULL,
     `updated_at` INTEGER DEFAULT NULL
);