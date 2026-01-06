# Autograding Setup - Instructor Guide

## Overview

This repository uses **Option 3** - hidden instructor tests stored in `.github/tests/` that are copied during autograding but invisible to students.

## How It Works

### For Students
- **Cannot see** the instructor test files in their repository
- **Can write** their own tests in `src/test/java/edu/course/eventplanner/`
- **Receive feedback** from autograding when they push code
- **Should focus on** implementing the required methods and data structures

### For Instructors
- **Instructor tests** are in `.github/tests/` directory (hidden from students)
- **During autograding**, GitHub Actions copies these tests to `src/test/java/edu/course/eventplanner/`
- **Tests run automatically** and provide feedback
- **Easy to update** - just edit files in `.github/tests/`

## File Structure

```
.github/
├── tests/                          # INSTRUCTOR TESTS (hidden from students)
│   ├── VenueSelectionTest.java
│   ├── GuestManagementTest.java
│   ├── SeatingAlgorithmTest.java
│   └── TaskWorkflowTest.java
├── scripts/
│   ├── check_coverage.py           # Validates student test coverage
│   └── check_readme.py             # Validates README documentation
└── workflows/
    ├── autograding.json            # Defines test cases and points
    └── classroom.yml               # GitHub Actions workflow

src/test/java/edu/course/eventplanner/
    # Empty for students - they write their own tests here
```

## Setting Up for GitHub Classroom

### Step 1: Create Template Repository
1. This repository should be your **template**
2. Make sure `.github/tests/` contains all instructor test files
3. Make sure `src/test/java/edu/course/eventplanner/` is **empty** or contains only example tests

### Step 2: Remove Instructor Tests from Student View
Before pushing to template repository, delete these files if they exist in student-visible directory:
```bash
rm src/test/java/edu/course/eventplanner/VenueSelectionTest.java
rm src/test/java/edu/course/eventplanner/GuestManagementTest.java
rm src/test/java/edu/course/eventplanner/SeatingAlgorithmTest.java
rm src/test/java/edu/course/eventplanner/TaskWorkflowTest.java
```

### Step 3: Create GitHub Classroom Assignment
1. Use this repository as the **template**
2. Enable **autograding**
3. GitHub Classroom will automatically use `.github/workflows/autograding.json`

### Step 4: Students Clone and Work
- Students get a clean repository without instructor tests
- They implement their code
- They write their own unit tests
- When they push, autograding runs with instructor tests

## Grading Breakdown (100 points)

### Automated (60 points)
- **Venue Selection Tests** - 10 points
- **Guest Management Tests** - 10 points
- **Seating Algorithm Tests** - 10 points
- **Task Workflow Tests** - 10 points
- **Student Unit Test Coverage** - 15 points
- **README Documentation** - 5 points

### Manual Review (40 points)
You'll need to manually review:
- **Venue selection logic** (algorithmic approach, efficiency) - 5 points
- **Guest management** (proper LinkedList/Map usage) - 10 points
- **Seating algorithm** (BST usage, grouping logic, queues) - 15 points

## Modifying Instructor Tests

To update or add tests:

1. **Edit files in `.github/tests/`**
   ```bash
   cd .github/tests/
   # Edit VenueSelectionTest.java, etc.
   ```

2. **Test locally (optional)**
   ```bash
   # Copy tests to test directory temporarily
   cp .github/tests/*.java src/test/java/edu/course/eventplanner/
   
   # Run Maven tests
   mvn test
   
   # Remove them (don't commit to student-visible area)
   rm src/test/java/edu/course/eventplanner/*Test.java
   ```

3. **Commit and push**
   ```bash
   git add .github/tests/
   git commit -m "Update instructor tests"
   git push
   ```

4. **Changes apply immediately** to new student pushes

## Coverage Requirements

The `check_coverage.py` script requires:
- **Minimum 60% code coverage**
- **At least 3 student-written test files** (excluding instructor tests)

## README Requirements

The `check_readme.py` script requires:
- **At least 300 words**
- **Discussion of data structures used**
- **Big-O complexity analysis**
- **Justification for design choices**

## Troubleshooting

### Tests not running
- Check `.github/workflows/classroom.yml` is present
- Verify `.github/workflows/autograding.json` is configured
- Check GitHub Actions tab for error logs

### Students can see instructor tests
- Make sure instructor tests are ONLY in `.github/tests/`
- Verify `.gitignore` includes the test files in student directory
- Check that template repository doesn't have tests in `src/test/`

### Coverage not working
- Ensure `pom.xml` has JaCoCo plugin (already configured)
- Check that students are writing tests
- View detailed coverage report in GitHub Actions logs

## Security Notes

- **`.github/` folder** is part of the repository but instructor tests are not in student workspace
- **Students cannot modify** `.github/workflows/` (protected by GitHub Classroom)
- **Students cannot see** what tests are being run until after autograding
- **Test files** are only copied during CI/CD, not in student clones

## Support

For issues with autograding:
1. Check GitHub Actions logs in the repository
2. Review `.github/workflows/classroom.yml`
3. Test locally by manually copying instructor tests
4. Verify all required methods exist with correct signatures

