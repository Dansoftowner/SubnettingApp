@file:JvmName("CLIApp")

package com.subnetting.cli

private val tasks = listOf(
    SimpleSubnetInfoTask(),
    SubnetPartitioningTask()
)

fun main() {
    println(Header())

    println("Specify the ip address with the subnet mask!")

    val (ip, mask) = readLine()!!.split(" ")

    println("Options: ")
    println(tasks.mapIndexed { index, cliTask -> "${index + 1}) ${cliTask.name}" }.joinToString("\n"))

    val option = readLine()!!.toInt()
    tasks[option - 1](ip, mask)
}